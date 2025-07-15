package com.openknights.app.feature.project.projectdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.openknights.app.core.designsystem.theme.KnightsTheme
import com.openknights.app.core.model.Project
import com.openknights.app.core.model.User
import com.openknights.app.core.model.Participant
import com.openknights.app.core.model.ProjectRole
import com.openknights.app.feature.project.R
import com.openknights.app.feature.project.projectdetail.component.ProjectDetailChips
import com.openknights.app.feature.project.projectdetail.component.ProjectDetailSpeaker
import com.openknights.app.feature.project.projectdetail.component.ProjectDetailTopAppBar
import com.openknights.app.feature.project.projectdetail.model.ProjectDetailUiState
import com.openknights.app.core.testing.FakeOpenKnightsData
import com.openknights.app.core.testing.FakeUsers


import androidx.navigation.NavController


@Composable
internal fun ProjectDetailScreen(
    projectId: String,
    navController: NavController,
    viewModel: ProjectDetailViewModel = hiltViewModel(),
) {
    val scrollState = rememberScrollState()
    val projectUiState by viewModel.projectUiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceDim)
            .systemBarsPadding()
            .verticalScroll(scrollState),
    ) {
        ProjectDetailTopAppBar(
            onBackClick = { navController.navigateUp() },
        )
        Box {
            when (val uiState = projectUiState) {
                is ProjectDetailUiState.Loading -> ProjectDetailLoading()
                is ProjectDetailUiState.Success -> ProjectDetailContent(uiState.project)
            }
        }
    }

    LaunchedEffect(projectId) {
        viewModel.fetchProject(projectId)
    }
}

@Composable
private fun ProjectDetailLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ProjectDetailContent(project: Project) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(end = 58.dp),
            text = project.title,
            style = KnightsTheme.typography.headlineMediumB,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
        Spacer(modifier = Modifier.height(12.dp))
        ProjectDetailChips(project = project)

        if (project.content.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            ProjectOverview(content = project.content)
        }

        Spacer(modifier = Modifier.height(40.dp))
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
        Spacer(modifier = Modifier.height(40.dp))

        val participants = FakeOpenKnightsData.fakeParticipants.filter { it.projectId == project.id }
        participants.forEach { participant ->
            val user = FakeUsers.users.first { it.id == participant.userId }
            ProjectDetailSpeaker(user = user, role = participant.role)
            if (participant != participants.last()) {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
private fun ProjectOverview(content: String) {
    Column {
        Text(
            text = stringResource(id = R.string.project_overview_title),
            style = KnightsTheme.typography.titleSmallB,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = content,
            style = KnightsTheme.typography.titleSmallR140,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

class ProjectDetailContentProvider : PreviewParameterProvider<Project> {
    override val values: Sequence<Project> = sequenceOf(
        FakeOpenKnightsData.fakeProjects.first()
    )
}

@Preview
@Composable
private fun ProjectDetailTopAppBarPreview() {
    KnightsTheme {
        ProjectDetailTopAppBar(
            onBackClick = { }
        )
    }
}

@Preview
@Composable
private fun ProjectDetailContentPreview(
    @PreviewParameter(ProjectDetailContentProvider::class) project: Project,
) {
    KnightsTheme {
        ProjectDetailContent(project = project)
    }
}

@Preview
@Composable
private fun ProjectDetailSpeakerPreview() {
    KnightsTheme {
        val user = FakeUsers.users.first()
        ProjectDetailSpeaker(user = user, role = ProjectRole.TEAM_LEADER)
    }
}

@Preview
@Composable
private fun ProjectOverviewPreview() {
    KnightsTheme {
        ProjectOverview(FakeOpenKnightsData.fakeProjects.first().content)
    }
}