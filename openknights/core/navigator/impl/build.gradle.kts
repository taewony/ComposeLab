plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.ksp)
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.openknights.app.core.navigator.impl"
    compileSdk = 36

    defaultConfig {
        minSdk = 32
    }
    kotlin {
        jvmToolchain(21)
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":openknights:core:navigator:api"))

    // Hilt 의존성
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
