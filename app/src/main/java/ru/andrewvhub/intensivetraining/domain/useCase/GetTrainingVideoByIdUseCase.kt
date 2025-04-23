package ru.andrewvhub.intensivetraining.domain.useCase

import ru.andrewvhub.intensivetraining.core.UseCase
import ru.andrewvhub.intensivetraining.data.models.TrainingVideo
import ru.andrewvhub.intensivetraining.domain.repositories.TrainingRepository

class GetTrainingVideoByIdUseCase(
    private val repository: TrainingRepository
): UseCase<GetTrainingVideoByIdUseCase.Params, TrainingVideo>() {
    override suspend fun execute(params: Params): TrainingVideo = repository.getTrainingVideoByID(params.id)

    data class Params(
        val id: Int
    )
}