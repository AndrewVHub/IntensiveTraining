package ru.andrewvhub.intensivetraining.api.models.response

import com.google.gson.annotations.SerializedName

data class TrainingVideoResponse(
	@SerializedName("id") val id: Int,
	@SerializedName("duration") val duration: Int? = null,
	@SerializedName("link") val link: String? = null
)