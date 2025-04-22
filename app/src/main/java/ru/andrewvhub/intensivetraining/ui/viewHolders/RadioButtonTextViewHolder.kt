package ru.andrewvhub.intensivetraining.ui.viewHolders

import ru.andrewvhub.intensivetraining.core.BaseViewHolder
import ru.andrewvhub.intensivetraining.databinding.ItemRadioButtonPickerBinding
import ru.andrewvhub.intensivetraining.ui.items.RadioButtonTextItem
import ru.andrewvhub.utils.extension.setOnThrottleClickListener

class RadioButtonTextViewHolder(private val viewBinding: ItemRadioButtonPickerBinding) :
    BaseViewHolder<RadioButtonTextItem>(viewBinding.root) {

    init {
        viewBinding.titleRadioButton.setOnThrottleClickListener {
            getItem()?.onClick?.invoke()
        }
    }

    override fun bind(item: RadioButtonTextItem) {
        updateTitle(item)
        updateChecked(item)
    }

    override fun update(item: RadioButtonTextItem, payloads: Set<*>) = payloads.forEach {
        when (it) {
            RadioButtonTextItem.TITLE_KEY -> updateTitle(item)
            RadioButtonTextItem.CHECKED_KEY -> updateChecked(item)
        }
    }

    private fun updateTitle(item: RadioButtonTextItem) = with(viewBinding) {
        titleRadioButton.text = item.title
    }

    private fun updateChecked(item: RadioButtonTextItem) = with(viewBinding) {
        titleRadioButton.isChecked = item.isChecked
    }
}