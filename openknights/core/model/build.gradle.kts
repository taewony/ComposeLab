plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.openknights.app.core.model"
    compileSdk = 36

    defaultConfig {
        minSdk = 32
    }

    kotlin {
        jvmToolchain(21)
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
}
