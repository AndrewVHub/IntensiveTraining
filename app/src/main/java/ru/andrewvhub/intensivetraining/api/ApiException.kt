package ru.andrewvhub.intensivetraining.api

import java.io.IOException

data class ApiException(override val message: String) : IOException()