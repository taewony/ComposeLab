plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
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
    composeCompiler {
        enableStrongSkippingMode = true
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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

    // Navigation Compose 의존성
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.navigation.compose)

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
    androidTestImplementation(libs.androidx.ui.test.junit4)
}