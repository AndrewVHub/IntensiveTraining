package ru.andrewvhub.intensivetraining.di

import org.koin.dsl.module
import ru.andrewvhub.intensivetraining.domain.useCase.GetTrainingsUseCase

val useCaseModule = module {
    single { GetTrainingsUseCase(get()) }
}