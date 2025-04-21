package ru.andrewvhub.intensivetraining.ui.items

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.andrewvhub.intensivetraining.core.BaseViewHolder
import ru.andrewvhub.intensivetraining.databinding.ItemEmptyBinding
import ru.andrewvhub.intensivetraining.ui.viewHolders.SimpleViewHolder

enum class ItemContentType {
    EmptyItemType {
        override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder<*> =
            SimpleViewHolder<EmptyItem>(
                ItemEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
    };

    abstract fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder<*>
}