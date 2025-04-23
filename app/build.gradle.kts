plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.safeArgs)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ru.andrewvhub.intensivetraining"
    compileSdk = 35

    viewBinding.enable = true
    buildFeatures.buildConfig = true

    defaultConfig {
        applicationId = "ru.andrewvhub.intensivetraining"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        val baseUrl = "API_BASE_URL"

        debug {
            isMinifyEnabled = false
            isDebuggable = true
            resValue("string", "app_name", "Intensive Training Debug")

            buildConfigField("String", baseUrl, "\"https://ref.test.kolsa.ru/\"")
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.core.animation)

    //Lifecycle
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata)

    //Koin
    implementation(libs.koin)

    //Navigation Component
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    //Glide
    implementation(libs.glide)

    //Timber
    implementation(libs.timber)

    //RecyclerView
    implementation(libs.androidx.recyclerView)

    //Lottie Animation
    implementation(libs.lottie.animation)

    //Gson
    implementation(libs.gson)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)

    //OkHttp3
    implementation(libs.logging.interceptor)

    //Chucker for checking HTTP
    implementation (libs.chucker)
    implementation(kotlin("reflect"))

    //SwipeRefreshLayout
    implementation(libs.androidx.swipeRefreshLayout)

    //Media ExoPlayer
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)
}