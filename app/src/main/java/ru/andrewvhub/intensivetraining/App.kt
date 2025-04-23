package ru.andrewvhub.intensivetraining

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import ru.andrewvhub.intensivetraining.di.appModule
import ru.andrewvhub.intensivetraining.di.netModule
import ru.andrewvhub.intensivetraining.di.repositoryModule
import ru.andrewvhub.intensivetraining.di.uiModule
import ru.andrewvhub.intensivetraining.di.useCaseModule
import timber.log.Timber
import timber.log.Timber.Forest.plant

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@App)
            fragmentFactory()
            modules(
                uiModule,
                appModule,
                netModule,
                repositoryModule,
                useCaseModule
            )
        }
    }
}