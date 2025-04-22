package ru.andrewvhub.intensivetraining.ui.items

data class CategoryItem(
    override val id: String,
    val title: String,
    val isSelected: Boolean,
    val onClick: () -> Unit

): Item {

    override fun getType(): ItemContentType  = ItemContentType.CategoryItemType

    override fun areContentsSame(newItem: Item): Boolean {
        newItem as CategoryItem
        return title == newItem.title && isSelected == newItem.isSelected
    }

    override fun getChangePayload(newItem: Item): Any {
        newItem as CategoryItem
        val diff = mutableSetOf<String>()
        if (title != newItem.title) diff.add(TITLE_KEY)
        if (isSelected != newItem.isSelected) diff.add(SELECTED_KEY)
        return diff
    }

    companion object {
        const val TITLE_KEY = "TITLE_KEY"
        const val SELECTED_KEY = "SELECTED_KEY"
    }
}