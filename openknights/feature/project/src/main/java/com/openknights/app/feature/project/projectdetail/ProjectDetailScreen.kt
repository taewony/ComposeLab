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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.openknights.app.core.designsystem.theme.KnightsTheme
import com.openknights.app.core.designsystem.theme.knightsTypography
import com.openknights.app.core.model.Project
import com.openknights.app.core.model.ProjectRole
import com.openknights.app.core.testing.FakeOpenKnightsData
import com.openknights.app.core.testing.FakeUsers
import com.openknights.app.feature.project.R
import com.openknights.app.feature.project.projectdetail.component.ProjectDetailChips
import com.openknights.app.feature.project.projectdetail.component.ProjectDetailSpeaker
import com.openknights.app.feature.project.projectdetail.component.ProjectDetailTopAppBar
import com.openknights.app.feature.project.projectdetail.model.ProjectDetailUiState

// Screen: Project Detail
@Composable
fun ProjectDetailScreen(
    projectId: String,
    onBack: () -> Unit
){
    val viewModel: ProjectDetailViewModel = viewModel()
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
            onBackClick = { onBack() },
        )
        Box {
            when (val uiState = projectUiState) {
                is ProjectDetailUiState.Loading -> ProjectDetailLoading()
                is ProjectDetailUiState.Success -> ProjectDetailContent(uiState.project)
                is ProjectDetailUiState.Error -> Text("Error: ${uiState.message}")
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

/**
 * `ProjectDetailContent`는 프로젝트 상세 정보를 표시하는 Composable입니다.
 * `MaterialTheme.knightsTypography`를 사용하여 커스텀 정의된 텍스트 스타일을 적용합니다.
 * 이는 Material Design 시스템의 일관성을 유지하면서도 앱의 고유한 디자인 요구사항을 충족하는 방법입니다.
 */
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
            style = MaterialTheme.knightsTypography.headlineMediumB,
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

/**
 * `ProjectOverview`는 프로젝트 개요를 표시하는 Composable입니다.
 * `MaterialTheme.knightsTypography`를 사용하여 커스텀 정의된 텍스트 스타일을 적용합니다.
 */
@Composable
private fun ProjectOverview(content: String) {
    Column {
        Text(
            text = stringResource(id = R.string.project_overview_title),
            style = MaterialTheme.knightsTypography.titleSmallB,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = content,
            style = MaterialTheme.knightsTypography.titleSmallR140,
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