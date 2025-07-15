package com.openknights.app.feature.project.projectnavigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.openknights.app.feature.project.projectdetail.ProjectDetailScreen
import com.openknights.app.feature.project.projectlist.ProjectListScreen

fun NavGraphBuilder.projectNavGraph(
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable<RouteProjectList> {
        val contestTerm: String = it.toRoute<RouteProjectList>().contestTerm
        var scrollToEventConsumed by rememberSaveable { mutableStateOf(false) }
        ProjectListScreen(
            contestTerm = contestTerm,
            // "선택한 프로젝트로 자동 스크롤" 기능 구현을 위한 Hook
            // val scrollToProjectId = ... // ViewModel 상태 등에서 받아오기
            scrollToProjectId = if (scrollToEventConsumed) null else null, // TODO: scrollToProjectId 구현
            onProjectClick = { project ->
                scrollToEventConsumed = true
            },
            onShowErrorSnackBar = onShowErrorSnackBar,
        )
    }

    composable<RouteProjectDetail> { navBackStackEntry ->
        val projectId = navBackStackEntry.toRoute<RouteProjectDetail>().projectId
        ProjectDetailScreen(projectId = projectId)
    }
}