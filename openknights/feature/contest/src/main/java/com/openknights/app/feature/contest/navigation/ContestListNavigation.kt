package com.openknights.app.feature.contest.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.PaddingValues
import com.openknights.app.feature.contest.ContestListScreen
import com.openknights.app.core.model.Contest
import com.openknights.app.feature.project.projectnavigation.RouteProjectList

const val CONTEST_LIST_ROUTE = "contest_list_route"

fun NavController.navigateToContestList(navOptions: NavOptions? = null) {
    this.navigate(CONTEST_LIST_ROUTE, navOptions)
}

fun NavGraphBuilder.contestListScreen(
    onContestClick: (Contest) -> Unit,
) {
    composable(route = CONTEST_LIST_ROUTE) {
        ContestListScreen(
            padding = PaddingValues(), // TODO: 실제 padding 전달 필요
            onContestClick = onContestClick
        )
    }
}