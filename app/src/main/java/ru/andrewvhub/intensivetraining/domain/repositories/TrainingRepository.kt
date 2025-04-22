package ru.andrewvhub.intensivetraining.domain.repositories

import ru.andrewvhub.intensivetraining.data.models.Training

interface TrainingRepository {

    suspend fun getTrainings(): List<Training>
}