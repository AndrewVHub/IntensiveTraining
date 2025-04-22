package ru.andrewvhub.intensivetraining.api.models.response

data class ApiErrorResponse(
    val error: Error
)

data class Error(
    val code: Int,
    val message: String
)