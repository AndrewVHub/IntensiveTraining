package ru.andrewvhub.intensivetraining.ui.items

data class TrainingItem(
    override val id: String,
    val icon: String,
    val time: String,
    val type: String,
    val description: String,
    val title: String,
    val onClick: () -> Unit
): Item {

    override fun getType(): ItemContentType  = ItemContentType.TrainingItemType

    override fun areContentsSame(newItem: Item): Boolean {
        newItem as TrainingItem
        return type == newItem.type
                && description == newItem.description
                && title == newItem.title
                && time == newItem.time
    }

    override fun getChangePayload(newItem: Item): Any {
        newItem as TrainingItem
        val diff = mutableSetOf<String>()
        if (type != newItem.type) diff.add(TYPE_KEY)
        if (description != newItem.description) diff.add(DESCRIPTION_KEY)
        if (title != newItem.title) diff.add(TITLE_KEY)
        if (time != newItem.time) diff.add(TIME_KEY)
        return diff
    }

    companion object {
        const val TYPE_KEY = "TYPE_KEY"
        const val DESCRIPTION_KEY = "DESCRIPTION_KEY"
        const val TITLE_KEY = "TITLE_KEY"
        const val TIME_KEY = "time"
    }
}