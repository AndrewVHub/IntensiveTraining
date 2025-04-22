package ru.andrewvhub.intensivetraining.ui.items

class RadioButtonTextItem(
    val title: String,
    val isChecked: Boolean,
    val onClick: () -> Unit
) : Item {
    override val id: String
        get() = title

    override fun getType(): ItemContentType = ItemContentType.RadioButtonTextItemType

    override fun areContentsSame(newItem: Item): Boolean {
        newItem as RadioButtonTextItem
        return title == newItem.title && isChecked == newItem.isChecked
    }

    override fun getChangePayload(newItem: Item): Any {
        newItem as RadioButtonTextItem
        val diff = mutableSetOf<String>()
        if (title != newItem.title) diff.add(TITLE_KEY)
        if (isChecked != newItem.isChecked) diff.add(CHECKED_KEY)
        return diff
    }

    companion object {
        const val TITLE_KEY = "TITLE_KEY"
        const val CHECKED_KEY = "CHECKED_KEY"
    }
}