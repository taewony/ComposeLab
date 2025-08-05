plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"

    // 여기에 구글 서비스 플러그인을 '적용'합니다.
    id("com.google.gms.google-services")
}

android {
    namespace = "com.openknights.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.openknights.app"
        minSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    kotlin {
        jvmToolchain(21)
    }

    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
dependencies {
    // --- 모듈 의존성 ---
    implementation(project(":openknights:core:designsystem"))
    implementation(project(":openknights:core:model"))
    implementation(project(":openknights:core:testing"))
    implementation(project(":openknights:feature:contest"))
    implementation(project(":openknights:feature:project"))
    implementation(project(":openknights:feature:user"))
    implementation(project(":openknights:feature:notice"))

    // --- Compose & UI 의존성 ---
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.material.icons.extended)

    // --- Navigation 의존성 ---
    implementation(libs.androidx.navigation3.runtime) 
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // --- 기타 라이브러리 ---
    implementation(libs.kotlinx.serialization.core)

    // --- 테스트 의존성 ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Firebase BoM 및 개별 라이브러리 종속성 추가,  Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:34.0.0"))

    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")

    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries
}