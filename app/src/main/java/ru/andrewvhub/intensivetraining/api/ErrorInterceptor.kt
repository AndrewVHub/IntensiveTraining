package ru.andrewvhub.intensivetraining.api

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.Response
import ru.andrewvhub.intensivetraining.api.models.response.ApiErrorResponse


class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (!response.isSuccessful) {
            val gson = GsonBuilder().create()
            val errorBody = response.body?.string().orEmpty()
            runCatching {
                gson.fromJson(errorBody, ApiErrorResponse::class.java).error
            }.getOrNull()?.let {
                throw ApiException(it.message)
            }
        }

        return response
    }
}