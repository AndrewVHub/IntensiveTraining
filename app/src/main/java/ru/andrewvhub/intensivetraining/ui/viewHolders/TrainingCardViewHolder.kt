package ru.andrewvhub.intensivetraining.ui.viewHolders

import androidx.core.view.isVisible
import ru.andrewvhub.intensivetraining.core.BaseViewHolder
import ru.andrewvhub.intensivetraining.databinding.ItemTrainingCardBinding
import ru.andrewvhub.intensivetraining.ui.items.TrainingItem
import ru.andrewvhub.utils.extension.load
import ru.andrewvhub.utils.extension.setOnThrottleClickListener

class TrainingCardViewHolder(private val viewBinding: ItemTrainingCardBinding) :
    BaseViewHolder<TrainingItem>(viewBinding.root) {

    init {
        viewBinding.root.setOnThrottleClickListener { getItem()?.onClick?.invoke() }
    }

    override fun bind(item: TrainingItem) {
        updateIcon(item)
        updateTitle(item)
        updateDescription(item)
        updateTime(item)
        updateType(item)
    }

    override fun update(item: TrainingItem, payloads: Set<*>) = payloads.forEach {
        when (it) {
            TrainingItem.TYPE_KEY -> updateType(item)
            TrainingItem.DESCRIPTION_KEY -> updateDescription(item)
            TrainingItem.TITLE_KEY -> updateTitle(item)
            TrainingItem.TIME_KEY -> updateTime(item)
        }
    }

    private fun updateTitle(item: TrainingItem): Unit = with(viewBinding) {
        trainingTitle.text = item.title
    }


    private fun updateDescription(item: TrainingItem): Unit = with(viewBinding) {
        trainingDescription.isVisible = item.description.isNotBlank()
        trainingDescription.text = item.description
    }

    private fun updateTime(item: TrainingItem): Unit = with(viewBinding) {
        trainingTime.text = item.time
    }

    private fun updateIcon(item: TrainingItem): Unit = with(viewBinding) {
        trainingImage.load(item.image)
    }

    private fun updateType(item: TrainingItem): Unit = with(viewBinding) {
        trainingType.text = item.type
    }
}