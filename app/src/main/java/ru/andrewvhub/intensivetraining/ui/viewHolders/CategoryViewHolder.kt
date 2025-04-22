package ru.andrewvhub.intensivetraining.ui.viewHolders

import ru.andrewvhub.intensivetraining.core.BaseViewHolder
import ru.andrewvhub.intensivetraining.databinding.ItemCategoryBinding
import ru.andrewvhub.intensivetraining.ui.items.CategoryItem

import ru.andrewvhub.utils.extension.setOnThrottleClickListener

class CategoryViewHolder(
    private val viewBinding: ItemCategoryBinding
) : BaseViewHolder<CategoryItem>(viewBinding.root) {

    init {
        viewBinding.root.setOnThrottleClickListener {
            getItem()?.onClick?.invoke()
        }

    }

    override fun bind(item: CategoryItem) {
        updateTitle(item)
        updateSelected(item)
    }

    override fun update(item: CategoryItem, payloads: Set<*>) = payloads.forEach {
        when (it) {
            CategoryItem.TITLE_KEY -> updateTitle(item)
            CategoryItem.SELECTED_KEY -> updateSelected(item)
        }
    }

    private fun updateSelected(item: CategoryItem): Unit = with(viewBinding) {
        chip.isChecked = item.isSelected
    }

    private fun updateTitle(item: CategoryItem): Unit = with(viewBinding) {
        chip.text = item.title
    }
}