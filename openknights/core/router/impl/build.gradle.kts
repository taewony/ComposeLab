plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.openknights.app.core.router.impl"
    compileSdk = 36

    defaultConfig {
        minSdk = 32
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":openknights:core:router:api"))

    // Hilt 의존성
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
