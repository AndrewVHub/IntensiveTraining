package ru.andrewvhub.intensivetraining.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResultListener
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.andrewvhub.intensivetraining.R
import ru.andrewvhub.intensivetraining.core.BaseFragment
import ru.andrewvhub.intensivetraining.databinding.FragmentHomeBinding
import ru.andrewvhub.intensivetraining.ui.bottomSheet.SelectFilterBottomSheetFragment
import ru.andrewvhub.intensivetraining.ui.itemDecorators.LinearLayoutItemDecorator
import ru.andrewvhub.intensivetraining.ui.items.TrainingItem
import ru.andrewvhub.intensivetraining.ui.viewBinding.viewBinding
import ru.andrewvhub.utils.adapter.Adapter
import ru.andrewvhub.utils.extension.addSystemBottomSpace
import ru.andrewvhub.utils.extension.addSystemTopSpace
import ru.andrewvhub.utils.extension.dp
import ru.andrewvhub.utils.extension.launchWhenStarted
import ru.andrewvhub.utils.extension.nonNullObserve
import ru.andrewvhub.utils.extension.setOnThrottleClickListener
import ru.andrewvhub.utils.extension.showSnackBar

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewBinding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel by viewModel<HomeViewModel>()

    private val adapterTrainingCards: Adapter by inject()

    private val endIconCross by lazy {
        ContextCompat.getDrawable(requireContext(), R.drawable.ic_close_circle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(viewBinding) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.addSystemTopSpace(false)

        swipeRefresh.setOnRefreshListener {
            viewModel.loadTrainings()
        }

        searchValue.doAfterTextChanged { text ->
            viewModel.onSearch(text.toString())
            searchLayout.endIconDrawable = endIconCross.takeIf { !text.isNullOrEmpty() }
        }
        searchLayout.setEndIconOnClickListener { resetFilters() }

        resetFilters.setOnThrottleClickListener {
            resetFilters()
        }

        filterButton.setOnThrottleClickListener {
            viewModel.navigateToSelectFilter()
        }

        exercisesRecyclerView.apply {
            addSystemBottomSpace(true)
            adapter = adapterTrainingCards
            addItemDecoration(
                LinearLayoutItemDecorator(
                    left = resources.getDimensionPixelSize(R.dimen.space_common_side),
                    right = resources.getDimensionPixelSize(R.dimen.space_common_side),
                    divider = 8.dp
                )
            )
        }

        setFragmentResultListener(SelectFilterBottomSheetFragment.INTENT) { _, bundle ->
            val data = bundle.getString(SelectFilterBottomSheetFragment.TYPE).orEmpty()
            if (data == DEFAULT_CATEGORY)
                resetFilters()
            else
                viewModel.onSelectType(data)
        }

        viewModel.apply {
            filteredTrainings.onEach(::handleTraining).launchWhenStarted(viewLifecycleOwner)
            selectedTypeFlow.onEach(::handleSelectedType).launchWhenStarted(viewLifecycleOwner)
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

    private fun handleLoading(isLoading: Boolean): Unit = with(viewBinding) {
        swipeRefresh.isRefreshing = isLoading
    }

    private fun handleSelectedType(selectedType: String): Unit = with(viewBinding) {
        currentFilterValue.isVisible = selectedType.isNotBlank()
        currentFilterValue.text = getString(R.string.home_fragment_current_filter_value, selectedType)
        if (selectedType != DEFAULT_CATEGORY)
            filterButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_filter_selected))
        else
            filterButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_filter))
    }

    private fun resetFilters(): Unit = with(viewBinding) {
        searchValue.text?.clear()
        //Можно оставить, тут вкусовщина
        searchValue.clearFocus()
        viewModel.resetFilters()
    }
}