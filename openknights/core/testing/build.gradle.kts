plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.openknights.app.core.testing.test"
    compileSdk = 36

    defaultConfig {
        minSdk = 32
    }

    kotlin {
        jvmToolchain(21)
    }
}

dependencies {
    implementation(project(":openknights:core:model"))
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
}
