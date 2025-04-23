package ru.andrewvhub.intensivetraining.data.repositories

import ru.andrewvhub.intensivetraining.api.Api
import ru.andrewvhub.intensivetraining.data.models.Training
import ru.andrewvhub.intensivetraining.data.models.TrainingVideo
import ru.andrewvhub.intensivetraining.data.models.toModel
import ru.andrewvhub.intensivetraining.domain.repositories.TrainingRepository

class TrainingRepositoryImpl(
    private val api: Api
): TrainingRepository {
    override suspend fun getTrainings(): List<Training> =
        api.getTrainings().map { it.toModel() }

    override suspend fun getTrainingVideoByID(id: Int): TrainingVideo =
        api.getTrainingVideoByID(id).toModel()
}