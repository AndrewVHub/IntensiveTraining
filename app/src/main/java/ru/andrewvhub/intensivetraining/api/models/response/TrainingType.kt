package ru.andrewvhub.intensivetraining.api.models.response

import androidx.annotation.StringRes
import ru.andrewvhub.intensivetraining.R

enum class TrainingType(
    @StringRes val nameResId: Int
) {
    UNKNOWN(nameResId = R.string.training_type_unknown),
    CARDIO(nameResId = R.string.training_type_cardio),
    STRENGTH(nameResId = R.string.training_type_strength),
    PILATES(nameResId = R.string.training_type_pilates);

    companion object {
        fun from(code: Int?): TrainingType =
            entries.find { it.ordinal == code } ?: UNKNOWN
    }
}