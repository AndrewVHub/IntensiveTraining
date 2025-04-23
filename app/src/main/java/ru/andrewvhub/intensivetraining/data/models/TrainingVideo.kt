package ru.andrewvhub.intensivetraining.data.models

import ru.andrewvhub.intensivetraining.BuildConfig
import ru.andrewvhub.intensivetraining.api.models.response.TrainingVideoResponse

data class TrainingVideo(
    val id: Int,
    val url: String
) {
    companion object {
        fun toCorrectUrl(url: String): String =
            BuildConfig.API_BASE_URL + url
    }
}

fun TrainingVideoResponse.toModel() = TrainingVideo(
    id = id,
    url = TrainingVideo.toCorrectUrl(link.orEmpty())
)