package ru.andrewvhub.intensivetraining.data.models

import ru.andrewvhub.intensivetraining.api.models.response.TrainingResponse
import ru.andrewvhub.intensivetraining.api.models.response.TrainingType

data class Training(
    val id: Int? = null,
    val title: String? = null,
    val image: String,
    val description: String? = null,
    val type: TrainingType,
    val duration: Int? = null
)


fun TrainingResponse.toModel() = Training(
    id = id,
    title = title,
    duration = duration,
    description = description,
    type = TrainingType.from(type),
    image = TrainingType.getImageFromType(type)
)