package ru.andrewvhub.utils.extension

import android.content.res.Resources
import ru.andrewvhub.intensivetraining.R
import ru.andrewvhub.intensivetraining.api.ApiException

fun Resources.getMessageFromThrowable(throwable: Throwable): String =
    when (throwable) {
        is ApiException -> throwable.message
        else -> getString(R.string.common_unknown_error)
    }