package ru.andrewvhub.intensivetraining.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.andrewvhub.intensivetraining.BuildConfig
import ru.andrewvhub.intensivetraining.api.Api
import ru.andrewvhub.intensivetraining.api.DurationRandomFallbackTypeAdapterFactory
import ru.andrewvhub.intensivetraining.api.ErrorInterceptor

val netModule = module {
    single<Api> {
        val durationConverter: Gson = GsonBuilder()
            .registerTypeAdapterFactory(DurationRandomFallbackTypeAdapterFactory())
            .create()

        val clientToWrap = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(ErrorInterceptor())
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        ChuckerInterceptor.Builder(get())
                            .collector(ChuckerCollector(get()))
                            .maxContentLength(250000L)
                            .redactHeaders(emptySet())
                            .alwaysReadResponseBody(false)
                            .build()
                    )
                }
            }
            .build()
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(clientToWrap)
            .addConverterFactory(GsonConverterFactory.create(durationConverter))
            .build().create(Api::class.java)
    }
}