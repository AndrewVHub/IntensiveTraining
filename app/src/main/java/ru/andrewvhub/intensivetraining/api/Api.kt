package ru.andrewvhub.intensivetraining.api

import retrofit2.http.GET
import ru.andrewvhub.intensivetraining.api.models.response.TrainingResponse

interface Api {

    @GET("get_workouts")
    suspend fun getTrainings(): List<TrainingResponse>

}