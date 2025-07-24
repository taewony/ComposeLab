plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.openknights.app.core.navigator.api"
    compileSdk = 36

    defaultConfig {
        minSdk = 32
    }

    kotlin {
        jvmToolchain(21)
    }
}
