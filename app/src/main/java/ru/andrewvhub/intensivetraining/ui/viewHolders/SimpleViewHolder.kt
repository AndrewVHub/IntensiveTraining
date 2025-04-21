package ru.andrewvhub.intensivetraining.ui.viewHolders

import androidx.viewbinding.ViewBinding
import ru.andrewvhub.intensivetraining.core.BaseViewHolder
import ru.andrewvhub.intensivetraining.ui.items.Item

class SimpleViewHolder<T : Item>(viewBinding: ViewBinding) : BaseViewHolder<T>(viewBinding.root) {
    override fun bind(item: T) {}
}