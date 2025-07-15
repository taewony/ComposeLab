package com.openknights.app.feature.project.projectlist.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.openknights.app.core.designsystem.theme.KnightsColor
import com.openknights.app.core.designsystem.theme.KnightsTheme
import com.openknights.app.core.model.Project
import com.openknights.app.core.model.ProjectPhase
import com.openknights.app.core.testing.FakeOpenKnightsData
import com.openknights.app.core.ui.KnightsCard
import com.openknights.app.core.ui.NetworkImage
import com.openknights.app.core.ui.OutlineChip
import androidx.compose.foundation.layout.Arrangement

@Composable
internal fun ProjectCard(
    project: Project,
    modifier: Modifier = Modifier,
    isHighlighted: Boolean = false,
    onProjectClick: (Project) -> Unit = {},
    index: Int = 0 // index 파라미터 추가
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isHighlighted) {
            KnightsColor.Blue03
        } else if (index % 2 == 0) { // 짝수 인덱스는 흰색
            MaterialTheme.colorScheme.surface
        } else { // 홀수 인덱스는 흐린 회색
            KnightsColor.PaleGray
        },
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing,
        ),
        label = "itemBackgroundColor",
    )
    KnightsCard(
        modifier = modifier,
        color = backgroundColor,
        onClick = { onProjectClick(project) }
    ) {
        ProjectCardContent(project = project)
    }
}

@Composable
private fun ProjectCardContent(
    project: Project,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(CardContentPadding)
    ) {
        ProjectTitle(project.title)
        Spacer(modifier = Modifier.height(4.dp))
        ProjectTeamName(project.teamName)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            ProjectPhaseChip(project.phase)
        }
    }
}

@Composable
private fun ProjectTitle(
    projectTitle: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = projectTitle,
        style = KnightsTheme.typography.titleLargeB,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = modifier
    )
}

@Composable
private fun ProjectTeamName(
    teamName: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = teamName,
        style = KnightsTheme.typography.labelLargeM,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        modifier = modifier
    )
}

@Composable
private fun ProjectPhaseChip(
    projectPhase: ProjectPhase,
    modifier: Modifier = Modifier,
) {
    OutlineChip(
        text = projectPhase.label,
        borderColor = MaterialTheme.colorScheme.primary,
        textColor = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}

private val CardContentPadding =
    PaddingValues(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 16.dp)

class ProjectPreviewParameterProvider : PreviewParameterProvider<Project> {
    override val values: Sequence<Project> = sequenceOf(
        FakeOpenKnightsData.fakeProjects.first()
    )
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ProjectCardPreview(@PreviewParameter(ProjectPreviewParameterProvider::class) project: Project) {
    KnightsTheme {
        ProjectCard(project)
    }
}