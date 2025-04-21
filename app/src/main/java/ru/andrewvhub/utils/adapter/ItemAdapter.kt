package ru.andrewvhub.utils.adapter

import ru.andrewvhub.intensivetraining.ui.items.Item

interface ItemAdapter {
    fun getItemByPosition(position: Int): Item?
}