plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.droidknights.app.feature.contest"
    compileSdk = 36

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeBom.get()
    }

    packaging {
        resources {
            excludes.add("META-INF/**")
        }
    }
    defaultConfig {
        minSdk = 32
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    kotlin {
        jvmToolchain(21)
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // core 모듈 의존성
    implementation(project(":openknights:core:model"))
    implementation(project(":openknights:core:designsystem"))
    implementation(project(":openknights:core:testing"))
    implementation(project(":openknights:feature:project"))

    // DroidKnightsApp의 feature 플러그인에서 가져온 의존성들
    implementation(libs.kotlinx.immutable)
    implementation(libs.compose.shimmer)

    // Hilt 의존성
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    kspAndroidTest(libs.hilt.compiler)
    kspTest(libs.hilt.compiler)

    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    // Navigation Compose와 함께 ViewModel을 사용하기 위한 올바른 라이브러리
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
    implementation(libs.kotlinx.serialization.core)

    // Compose UI 의존성
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // androidTestImplementation(libs.androidx.ui.test.junit4)
}