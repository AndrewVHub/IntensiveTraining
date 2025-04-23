package ru.andrewvhub.intensivetraining.domain.repositories

import ru.andrewvhub.intensivetraining.data.models.Training
import ru.andrewvhub.intensivetraining.data.models.TrainingVideo

interface TrainingRepository {
    suspend fun getTrainings(): List<Training>
    suspend fun getTrainingVideoByID(id: Int): TrainingVideo
}