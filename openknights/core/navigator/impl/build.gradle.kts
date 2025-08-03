plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    //id("org.jetbrains.kotlin.jvm")
}


android {
    // A unique identifier for this library module.
    namespace = "com.openknights.app.core.navigator.impl"

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
dependencies {
    implementation(project(":openknights:core:navigator:api"))
    implementation("javax.inject:javax.inject:1")
}
