plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.openknights.feature.auth"
    compileSdk = 36

    defaultConfig {
        minSdk = 32
    }

    buildFeatures {
        compose = true
    }

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    // In-project dependencies
    implementation(project(":openknights:core:designsystem"))
    implementation(project(":openknights:core:data:user"))
    implementation(project(":openknights:core:model"))

    // AndroidX & Compose
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Firebase BoM 및 개별 라이브러리 종속성 추가,  Import the Firebase BoM
    implementation(libs.firebase.auth)
    implementation(platform(libs.firebase.bom))
}
