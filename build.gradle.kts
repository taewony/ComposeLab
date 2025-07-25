// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Gradle은 빌드 자동화 툴이고,
// 특정 기능(예: Android 앱 빌드, Kotlin 코드 컴파일, Compose 지원 등)을 추가하려면 플러그인(Plugin)을 적용 필요함
// apply false는 "해당 플러그인을 현재 모듈에 즉시 적용하지 않는다"는 의미입니다.
plugins {
    alias(libs.plugins.android.application) apply false // 안드로이드 앱용
    alias(libs.plugins.android.library) apply false // 안드로이드 라이브러리 모듈용

    alias(libs.plugins.kotlin.android) apply false
    

    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false  // KSP (Kotlin Symbol Processing)  Room, Hilt, Moshi 등 annotation processing 최적화
}