package com.openknights.app.feature.user.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.openknights.app.feature.user.UserScreen

const val USER_ROUTE = "user_route"

fun NavController.navigateToUser(navOptions: NavOptions? = null) {
    this.navigate(USER_ROUTE, navOptions)
}

fun NavGraphBuilder.userScreen() {
    composable(route = USER_ROUTE) {
        UserScreen()
    }
}