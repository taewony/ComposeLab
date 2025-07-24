plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.ksp)
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.openknights.app.core.data"
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
    implementation(project(":openknights:core:model"))

    // Hilt 의존성
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
