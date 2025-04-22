package ru.andrewvhub.intensivetraining.api.models.response

import com.google.gson.annotations.SerializedName

data class TrainingResponse(
	@SerializedName("id") val id: Int? = null,
	@SerializedName("title") val title: String? = null,
	@SerializedName("description") val description: String? = null,
	@SerializedName("type") val type: Int? = null,
	@SerializedName(DURATION) val duration: Int? = null
) {
	companion object {
		const val DURATION = "duration"
	}
}