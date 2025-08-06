pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://androidx.dev/snapshots/builds/latest/artifacts/repository") }
    }
}

rootProject.name = "composeLab"
include(":app")
include(":app_01_compose_coffee")
include(":app_02_kakao_email")
include(":app_03_profile")
include(":app_04_keypad")
include(":app_05_count")
include(":app_06_stopwatch")
include(":app_10_bubble_game")
include(":app_11_dropdown")
include(":app_11_jetpack")
include(":app_12_material_design")
include(":app_13_todotask")
include(":app_14_triple")
include(":app_17_todo_revised")
include(":app_18_fake_store")
include(":app_22_wellness")
include(":app_22_newsapp")
include(":app_22_state")
include(":app_22_box")

include(":openknights:app")
include(":openknights:core:designsystem")
include(":openknights:core:model")
include(":openknights:core:testing")
include(":openknights:feature:contest")
include(":openknights:feature:project")
include(":openknights:feature:user")
include(":openknights:feature:notice")
include(":openknights:feature:auth")
include(":openknights:core:ui")
include(":openknights:core:data")
include(":openknights:core:data:user")