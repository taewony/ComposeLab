plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    // A unique identifier for this library module.
    namespace = "com.openknights.app.core.navigator.api"

    // The Android SDK version to compile your code against.
    compileSdk = 36

    defaultConfig {
        // The minimum Android version your library will support.
        minSdk = 32
    }
}

kotlin {
    jvmToolchain(21)
}