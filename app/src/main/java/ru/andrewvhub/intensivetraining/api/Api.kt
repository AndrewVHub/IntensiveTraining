package ru.andrewvhub.intensivetraining.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.andrewvhub.intensivetraining.api.models.response.TrainingResponse
import ru.andrewvhub.intensivetraining.api.models.response.TrainingVideoResponse

interface Api {

    @GET("get_workouts")
    suspend fun getTrainings(): List<TrainingResponse>

    @GET("get_video")
    suspend fun getTrainingVideoByID(
        @Query("id") id: Int
    ): TrainingVideoResponse
}