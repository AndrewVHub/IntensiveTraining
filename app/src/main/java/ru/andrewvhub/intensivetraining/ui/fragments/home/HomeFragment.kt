package ru.andrewvhub.intensivetraining.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.andrewvhub.intensivetraining.R
import ru.andrewvhub.intensivetraining.core.BaseFragment
import ru.andrewvhub.intensivetraining.databinding.FragmentHomeBinding
import ru.andrewvhub.intensivetraining.ui.itemDecorators.LinearLayoutItemDecorator
import ru.andrewvhub.intensivetraining.ui.items.CategoryItem
import ru.andrewvhub.intensivetraining.ui.items.TrainingItem
import ru.andrewvhub.intensivetraining.ui.viewBinding.viewBinding
import ru.andrewvhub.utils.adapter.Adapter
import ru.andrewvhub.utils.extension.SystemSpaceTypes
import ru.andrewvhub.utils.extension.addSystemBottomSpace
import ru.andrewvhub.utils.extension.addSystemTopSpace
import ru.andrewvhub.utils.extension.dp
import ru.andrewvhub.utils.extension.launchWhenStarted
import ru.andrewvhub.utils.extension.nonNullObserve
import ru.andrewvhub.utils.extension.setOnThrottleClickListener
import ru.andrewvhub.utils.extension.showSnackBar
import kotlin.properties.Delegates

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewBinding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel by viewModel<HomeViewModel>()

    private val adapterTrainingCards: Adapter by inject()
    private val adapterCategory: Adapter by inject()

    private val categoryNames by lazy {
        resources.getStringArray(R.array.categories).toList()
    }
    private var selectedCategory: String by Delegates.observable("") { _, old, new ->
        if (old != new || new == "") {
            adapterCategory.setCollection(categoryNames.mapIndexed { index, name -> name.toItem(index) })
        }
    }

    private val endIconCross by lazy {
        ContextCompat.getDrawable(requireContext(), R.drawable.ic_close_circle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(viewBinding) {
        super.onViewCreated(view, savedInstanceState)

        selectedCategory = categoryNames.first()

        toolbar.addSystemTopSpace(false)

        swipeRefresh.setOnRefreshListener {
            viewModel.loadTrainings()
        }

        searchValue.doAfterTextChanged { text ->
            viewModel.onSearch(text.toString())
            searchLayout.endIconDrawable = endIconCross.takeIf { !text.isNullOrEmpty() }
        }
        searchLayout.setEndIconOnClickListener { clearTextAndFocus() }

        resetFilters.setOnThrottleClickListener {
            selectedCategory = categoryNames.first()
            clearTextAndFocus()
            viewModel.resetFilters()
        }

        exercisesRecyclerView.apply {
            addSystemBottomSpace(true, type = SystemSpaceTypes.BARS_AND_KEYBOARD)
            adapter = adapterTrainingCards
            addItemDecoration(
                LinearLayoutItemDecorator(
                    left = resources.getDimensionPixelSize(R.dimen.space_common_side),
                    right = resources.getDimensionPixelSize(R.dimen.space_common_side),
                    divider = 8.dp
                )
            )
        }

        categoryRecyclerView.apply {
            adapter = adapterCategory
            addItemDecoration(
                LinearLayoutItemDecorator(
                    left = resources.getDimensionPixelSize(R.dimen.space_common_side),
                    right = resources.getDimensionPixelSize(R.dimen.space_common_side),
                    divider = 8.dp,
                    orientation = RecyclerView.HORIZONTAL
                )
            )
        }

        viewModel.apply {
            filteredTrainings.onEach(::handleTraining).launchWhenStarted(viewLifecycleOwner)
            currentIndexCategoryFlow.onEach(::handleCurrentCategory).launchWhenStarted(viewLifecycleOwner)
            isLoading.nonNullObserve(viewLifecycleOwner,::handleLoading)
            errorMessage.nonNullObserve(viewLifecycleOwner) { showSnackBar(it) }
        }
    }

    private fun handleTraining(trainings: List<TrainingItem>): Unit = with(viewBinding) {
        if (trainings.isEmpty()) {
            loader.resumeAnimation()
            notFoundGroup.isVisible = true
            exercisesRecyclerView.isVisible = false
        } else {
            loader.resumeAnimation()
            notFoundGroup.isVisible = false
            exercisesRecyclerView.isVisible = true
            adapterTrainingCards.setCollection(trainings) {
                viewBinding.exercisesRecyclerView.scrollToPosition(0)
            }
        }
    }

    private fun handleCurrentCategory(index: Int?): Unit = with(viewBinding) {
        index?.let {
            (categoryRecyclerView.layoutManager as LinearLayoutManager)
                .scrollToPositionWithOffset(index, 30.dp)
        }
    }

    private fun handleLoading(isLoading: Boolean): Unit = with(viewBinding) {
        swipeRefresh.isRefreshing = isLoading
    }

    private fun clearTextAndFocus(): Unit = with(viewBinding) {
        searchValue.text?.clear()
        //Можно оставить, тут вкусовщина
        searchValue.clearFocus()
    }

    private fun String.toItem(index: Int) = CategoryItem(
        id = this,
        title = this,
        isSelected = this == selectedCategory,
        onClick = {
            selectedCategory = this
            viewModel.onSelectType(index, this)
        }
    )
}