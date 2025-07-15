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
import com.openknights.app.core.testing.FakeOpenKnightsData
import com.openknights.app.core.ui.KnightsCard
import com.openknights.app.core.ui.NetworkImage
import com.openknights.app.core.ui.OutlineChip

@Composable
internal fun ProjectCard(
    project: Project,
    modifier: Modifier = Modifier,
    isHighlighted: Boolean = false,
    onProjectClick: (Project) -> Unit = {},
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isHighlighted) {
            KnightsColor.Blue03 // White
        } else {
            MaterialTheme.colorScheme.surface
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
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(CardContentPadding)
        ) {
            ProjectHeader(project)
            Spacer(modifier = Modifier.height(8.dp))
            ProjectTitle(project.title)
            Spacer(modifier = Modifier.height(12.dp))
            ProjectPhaseInfo(project)
            Spacer(modifier = Modifier.height(12.dp))
            ProjectTeamMembers(project.id) // Pass projectId to fetch participants
        }
    }
}

@Composable
private fun ProjectHeader(
    project: Project,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlineChip(
            text = project.teamName,
            borderColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.onSurface
        )

        project.tags.forEach { tag ->
            Text(
                text = tag.name,
                style = KnightsTheme.typography.labelLargeM,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier
                    .padding(start = 8.dp)
            )
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
            .padding(end = 50.dp)
    )
}

@Composable
private fun ProjectPhaseInfo(
    project: Project,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
        OutlineChip(
            text = project.phase.displayName,
            borderColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ProjectTeamMembers(
    projectId: String,
    modifier: Modifier = Modifier,
) {
    val participants = FakeOpenKnightsData.fakeParticipants.filter { it.projectId == projectId }
    val users = participants.map { participant ->
        FakeOpenKnightsData.fakeUsers.first { it.id == participant.userId }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
        ) {
            users.forEach { user ->
                Text(
                    text = user.name,
                    style = KnightsTheme.typography.titleLargeB,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            users.forEach { user ->
                NetworkImage(
                    imageUrl = user.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}

private val CardContentPadding =
    PaddingValues(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 24.dp)

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