plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.openknights.app.core.common"
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
    implementation("androidx.annotation:annotation:1.7.1")
}
