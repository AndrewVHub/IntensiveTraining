package ru.andrewvhub.intensivetraining.ui.items

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.andrewvhub.intensivetraining.core.BaseViewHolder
import ru.andrewvhub.intensivetraining.databinding.ItemEmptyBinding
import ru.andrewvhub.intensivetraining.databinding.ItemRadioButtonPickerBinding
import ru.andrewvhub.intensivetraining.databinding.ItemTrainingCardBinding
import ru.andrewvhub.intensivetraining.ui.viewHolders.RadioButtonTextViewHolder
import ru.andrewvhub.intensivetraining.ui.viewHolders.SimpleViewHolder
import ru.andrewvhub.intensivetraining.ui.viewHolders.TrainingCardViewHolder

enum class ItemContentType {
    EmptyItemType {
        override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder<*> =
            SimpleViewHolder<EmptyItem>(
                ItemEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
    },
    TrainingItemType {
        override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder<*> =
            TrainingCardViewHolder(
                ItemTrainingCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    },
    RadioButtonTextItemType {
        override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder<*> =
            RadioButtonTextViewHolder(
                ItemRadioButtonPickerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    };

    abstract fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder<*>
}