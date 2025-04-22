package ru.andrewvhub.intensivetraining.di

import org.koin.dsl.module
import ru.andrewvhub.intensivetraining.data.repositories.TrainingRepositoryImpl
import ru.andrewvhub.intensivetraining.domain.repositories.TrainingRepository

val repositoryModule = module {
    single<TrainingRepository> { TrainingRepositoryImpl(get()) }
}