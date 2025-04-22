package ru.andrewvhub.intensivetraining.ui.bottomSheet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import org.koin.android.ext.android.inject
import ru.andrewvhub.intensivetraining.R
import ru.andrewvhub.intensivetraining.core.BaseBottomSheetDialogFragment
import ru.andrewvhub.intensivetraining.databinding.FragmentSelectFilterBottomSheetBinding
import ru.andrewvhub.intensivetraining.ui.fragments.home.DEFAULT_CATEGORY
import ru.andrewvhub.intensivetraining.ui.itemDecorators.LinearLayoutItemDecorator
import ru.andrewvhub.intensivetraining.ui.items.RadioButtonTextItem
import ru.andrewvhub.intensivetraining.ui.viewBinding.viewBinding
import ru.andrewvhub.utils.adapter.Adapter
import ru.andrewvhub.utils.extension.dp
import ru.andrewvhub.utils.extension.setOnThrottleClickListener
import kotlin.properties.Delegates

class SelectFilterBottomSheetFragment : BaseBottomSheetDialogFragment(R.layout.fragment_select_filter_bottom_sheet) {

    private val viewBinding by viewBinding(FragmentSelectFilterBottomSheetBinding::bind)
    private val args: SelectFilterBottomSheetFragmentArgs by navArgs()

    private val adapter: Adapter by inject()

    private val categoryNames by lazy {
        resources.getStringArray(R.array.categories).toList()
    }

    private var selectedFilter by Delegates.observable(NO_ONE_SELECTED) { _, old, new ->
        if (old != new || new == NO_ONE_SELECTED) {
            adapter.setCollection(categoryNames.toItems())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(viewBinding) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            LinearLayoutItemDecorator(
                left = resources.getDimensionPixelSize(R.dimen.space_common_side),
                right = resources.getDimensionPixelSize(R.dimen.space_common_side),
                divider = 24.dp
            )
        )
        selectedFilter = args.selectedFilter.takeIf { args.selectedFilter.isNotEmpty() && args.selectedFilter != DEFAULT_CATEGORY } ?: categoryNames.first()

        resetFiltersButton.setOnThrottleClickListener {
            setFragmentResult(DEFAULT_CATEGORY)
        }

        continueButton.setOnThrottleClickListener {
            setFragmentResult(selectedFilter)
        }
    }

    private fun setFragmentResult(currentFilter: String) {
        setFragmentResult(
            INTENT,
            Bundle().apply {
                putString(TYPE, currentFilter)
            }
        )
        dismiss()
    }

    private fun List<String>.toItems(): List<RadioButtonTextItem> = map { it.toItem() }

    private fun String.toItem() = RadioButtonTextItem(
        title = this,
        isChecked = selectedFilter == this,
        onClick = {
            selectedFilter = this
        }
    )
    companion object {
        const val INTENT = "BOTTOM_SHEET_SELECT_INTENT"
        const val TYPE = "BOTTOM_SHEET_SELECT_DATA"
        const val NO_ONE_SELECTED = "NO_ONE_SELECTED"
    }
}