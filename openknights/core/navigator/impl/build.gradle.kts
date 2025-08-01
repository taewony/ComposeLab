plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose) // ✅ toml 별칭 사용으로 변경
    //alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
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
