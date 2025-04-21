package ru.andrewvhub.intensivetraining.di

import androidx.recyclerview.widget.DiffUtil
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.andrewvhub.intensivetraining.ui.fragments.home.HomeViewModel
import ru.andrewvhub.intensivetraining.ui.items.Item
import ru.andrewvhub.intensivetraining.ui.items.ItemCallback
import ru.andrewvhub.utils.adapter.Adapter

val uiModule = module {

    viewModel { HomeViewModel() }

    factory<DiffUtil.ItemCallback<Item>> { ItemCallback() }
    factory { Adapter(get()) }
}