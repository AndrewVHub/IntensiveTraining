package ru.andrewvhub.intensivetraining.domain.useCase

import ru.andrewvhub.intensivetraining.core.UseCase
import ru.andrewvhub.intensivetraining.data.models.Training
import ru.andrewvhub.intensivetraining.domain.repositories.TrainingRepository

class GetTrainingsUseCase(
    private val repository: TrainingRepository
): UseCase<Unit, List<Training>>() {
    override suspend fun execute(params: Unit): List<Training> = repository.getTrainings()
}