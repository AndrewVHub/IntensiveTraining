package ru.andrewvhub.intensivetraining.api.models.response

import androidx.annotation.StringRes
import ru.andrewvhub.intensivetraining.R

enum class TrainingType(
    @StringRes val nameResId: Int,
    val imageUrl: String
) {
    UNKNOWN(nameResId = R.string.training_type_unknown, imageUrl = "https://images.unsplash.com/photo-1584827386894-fc939dad6078?q=80&w=3540&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
    CARDIO(nameResId = R.string.training_type_cardio, imageUrl = "https://images.unsplash.com/photo-1614691771330-13f4e0deec54?q=80&w=3072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
    STRENGTH(nameResId = R.string.training_type_strength, imageUrl = "https://images.unsplash.com/photo-1517836357463-d25dfeac3438?q=80&w=3540&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
    PILATES(nameResId = R.string.training_type_pilates, imageUrl = "https://images.unsplash.com/photo-1731325632687-51e90609e700?q=80&w=3431&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");

    companion object {
        fun from(code: Int?): TrainingType =
            entries.find { it.ordinal == code } ?: UNKNOWN

        fun getImageFromType(type: Int?): String =
            entries.find { it.ordinal == type }?.imageUrl ?: UNKNOWN.imageUrl
    }
}