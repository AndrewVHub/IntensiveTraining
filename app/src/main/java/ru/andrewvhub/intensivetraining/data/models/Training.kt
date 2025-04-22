package ru.andrewvhub.intensivetraining.data.models

import ru.andrewvhub.intensivetraining.api.models.response.TrainingResponse
import ru.andrewvhub.intensivetraining.api.models.response.TrainingType

data class Training(
    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val type: TrainingType,
    val duration: Int? = null
)

fun TrainingResponse.toModel() = Training(
    duration = duration,
    description = description,
    id = id,
    title = title,
    type = TrainingType.from(type)
)