plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose) // ✅ toml 별칭 사용으로 변경
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
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
    implementation(project(":openknights:core:navigator:impl")) 

    // --- Hilt 의존성 ---
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    ksp(project(":openknights:core:navigator:impl"))

    // --- Compose & UI 의존성 ---
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.material.icons.extended)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")

    // --- Navigation 의존성 ---
    // 'navigation3'가 표준 라이브러리가 아니라면, 표준 라이브러리 사용을 고려해보세요.
    // 예: implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation3.runtime) 
    implementation(libs.androidx.navigation3.ui)    

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
}