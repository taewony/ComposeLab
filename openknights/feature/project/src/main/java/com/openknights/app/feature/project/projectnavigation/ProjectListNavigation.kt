package com.openknights.app.feature.project.projectnavigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.openknights.app.feature.project.projectdetail.ProjectDetailScreen
import com.openknights.app.feature.project.projectlist.ProjectListScreen

const val PROJECT_LIST_ROUTE = "project_list_route"
const val PROJECT_DETAIL_ROUTE = "project_detail_route"
const val PROJECT_ID_ARG = "projectId"
const val CONTEST_TERM_ARG = "contestTerm"

const val PROJECT_DETAIL_ROUTE_WITH_ARGS = "$PROJECT_DETAIL_ROUTE/{$PROJECT_ID_ARG}"

fun NavGraphBuilder.projectNavGraph(
    navController: NavController,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable(PROJECT_LIST_ROUTE) {
        val contestTerm: String = it.arguments?.getString(CONTEST_TERM_ARG) ?: "" // Provide default empty string
        var scrollToEventConsumed by rememberSaveable { mutableStateOf(false) }
        ProjectListScreen(
            contestTerm = contestTerm,
            // "선택한 프로젝트로 자동 스크롤" 기능 구현을 위한 Hook
            // val scrollToProjectId = ... // ViewModel 상태 등에서 받아오기
            scrollToProjectId = if (scrollToEventConsumed) null else null, // TODO: scrollToProjectId 구현
            onProjectClick = { project ->
                scrollToEventConsumed = true
                navController.navigate("$PROJECT_DETAIL_ROUTE/${project.id}")
            },
            onShowErrorSnackBar = onShowErrorSnackBar,
        )
    }

    composable(
        route = PROJECT_DETAIL_ROUTE_WITH_ARGS,
        arguments = listOf(navArgument(PROJECT_ID_ARG) { type = NavType.IntType })
    ) { navBackStackEntry ->
        val projectId = navBackStackEntry.arguments?.getInt(PROJECT_ID_ARG)?.toString() ?: "" // Convert Int? to String and provide default empty string
        ProjectDetailScreen(projectId = projectId, navController = navController)
    }
}