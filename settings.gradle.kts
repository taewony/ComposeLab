pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "composeLab"
include(":app")
// include(":app_01_compose_coffee")
// include(":app_02_kakao_email")
// include(":app_03_profile")
// include(":app_04_keypad")
// include(":app_05_count")
// include(":app_06_stopwatch")
// include(":app_10_bubble_game")
// include(":app_11_dropdown")
// include(":app_11_jetpack")
// include(":app_12_material_design")
// include(":app_13_todotask")
// include(":app_14_triple")
// include(":app_17_todo_revised")
// include(":app_18_fake_store")
// include(":app_22_wellness")
// include(":app_22_newsapp")
// include(":app_22_state")
// include(":app_22_box")
// include(":designsystem")
// include(":app_grid_ui")
// include(":app_overalpped_ui")
