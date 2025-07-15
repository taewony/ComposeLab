package com.openknights.app.feature.project.projectlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.openknights.app.core.designsystem.theme.KnightsTheme
import com.openknights.app.core.model.Project
import com.openknights.app.core.model.ProjectPhase
import com.openknights.app.feature.project.projectlist.component.ProjectCard
import com.openknights.app.feature.project.projectlist.component.ProjectListTopAppBar
import com.openknights.app.feature.project.projectlist.model.ProjectUiState
import com.openknights.app.feature.project.projectlist.model.rememberProjectState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun ProjectListScreen(
    contestTerm: String,
    onProjectClick: (Project) -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    scrollToProjectId: String? = null,
    projectListViewModel: ProjectListViewModel = hiltViewModel(),
) {
    val density = LocalDensity.current
    val projectUiState by projectListViewModel.uiState.collectAsStateWithLifecycle()
    val projectState = (projectUiState as? ProjectUiState.Projects)?.let { projects ->
        rememberProjectState(projects = projects.groups.flatMap { it.projects }.toPersistentList())
    } ?: rememberProjectState(projects = persistentListOf())

    LaunchedEffect(Unit) {
        projectListViewModel.fetchProjects(contestTerm)
        projectListViewModel.errorFlow.collectLatest { throwable -> onShowErrorSnackBar(throwable) }
    }

    var highlighted by remember { mutableStateOf(false) }

    LaunchedEffect(scrollToProjectId, projectState.groups) {
        scrollToProjectId?.let { projectId ->
            delay(300)
            val offset = with(density) { ((-6).dp).toPx().toInt() }
            projectState.scrollToProject(projectId, offset)
            delay(300)
            highlighted = true
            delay(500)
            highlighted = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ProjectListTopAppBar(
            projectState = projectState,
            onBackClick = projectListViewModel::navigateBack,
        )
        ProjectList(
            projectUiState = projectUiState,
            onProjectClick = { project ->
                onProjectClick(project)
                projectListViewModel.navigateProjectDetail(project.id)
            },
            modifier = Modifier
                .systemBarsPadding()
                .padding(top = 48.dp)
                .fillMaxSize(),
            highlightProjectId = if (highlighted) scrollToProjectId else null,
        )
    }
}

@Composable
private fun ProjectList(
    projectUiState: ProjectUiState,
    onProjectClick: (Project) -> Unit,
    modifier: Modifier = Modifier,
    highlightProjectId: String? = null,
) {
    when (projectUiState) {
        ProjectUiState.Loading -> {
            // TODO: Show loading indicator
        }
        is ProjectUiState.Projects -> {
            val projectState = rememberProjectState(projects = projectUiState.groups.flatMap { it.projects }.toPersistentList())
            LazyColumn(
                modifier = modifier,
                state = projectState.listState,
                contentPadding = PaddingValues(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                projectState.groups.forEach { group ->
                    item {
                        ProjectPhaseTitle(
                            projectPhase = group.projectPhase,
                            topPadding = if (group == projectState.groups.first()) ProjectTopSpace else ProjectGroupSpace,
                        )
                    }
                    items(items = group.projects) { project ->
                        ProjectCard(
                            project = project,
                            isHighlighted = project.id == highlightProjectId,
                            onProjectClick = onProjectClick,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProjectPhaseTitle(
    projectPhase: ProjectPhase,
    topPadding: Dp,
) {
    Column(
        modifier = Modifier
            .padding(start = 20.dp, top = topPadding, end = 20.dp)
    ) {
        Text(
            text = projectPhase.displayName,
            style = KnightsTheme.typography.titleLargeB,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.onPrimaryContainer)

        Spacer(
            modifier = Modifier
                .height(32.dp)
        )
    }
}

private val ProjectTopSpace = 16.dp
private val ProjectGroupSpace = 100.dp

@Preview(showBackground = true)
@Composable
private fun ProjectListScreenPreview() {
    ProjectListScreen(
        contestTerm = "",
        onProjectClick = {},
        onShowErrorSnackBar = {},
    )
}