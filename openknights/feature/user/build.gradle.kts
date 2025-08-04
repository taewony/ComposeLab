plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.hilt)           // <-- 안전한 사용
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}
android {
    namespace = "com.openknights.app.feature.user"
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
    implementation(project(":openknights:core:ui"))
    implementation(project(":openknights:core:data"))

    // DroidKnightsApp의 feature 플러그인에서 가져온 의존성들
    implementation(libs.kotlinx.immutable)
    implementation(libs.compose.shimmer)

    // Hilt 의존성
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Navigation3 의존성
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.kotlinx.serialization.core)

    // Lifecycle Compose 의존성
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

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