plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.openknights.core.data.user"
    compileSdk = 36

    defaultConfig {
        minSdk = 32
    }

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    // In-project dependencies
    implementation(project(":openknights:core:model"))

    // Firebase
    // implementation(platform(libs.firebase.bom))
    // implementation(libs.firebase.auth)
    // implementation(libs.firebase.firestore)

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
}
