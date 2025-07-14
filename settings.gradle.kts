pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com.android.*")
                includeGroupByRegex("com.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
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
include(":app_13_todotask")
include(":app_22_wellness")
include(":app_22_newsapp")
include(":app_22_state")
include(":app_22_box")
include(":openknights:core:designsystem")
