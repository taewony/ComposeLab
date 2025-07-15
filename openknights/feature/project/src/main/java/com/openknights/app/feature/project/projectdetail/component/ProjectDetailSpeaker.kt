package com.openknights.app.feature.project.projectdetail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openknights.app.core.ui.NetworkImage
import com.openknights.app.core.designsystem.theme.KnightsTheme
import com.openknights.app.core.model.User
import com.openknights.app.core.model.ProjectRole
import com.openknights.app.feature.project.R
import com.openknights.app.core.testing.FakeOpenKnightsData

@Composable
internal fun ProjectDetailSpeaker(
    user: User,
    role: ProjectRole,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        NetworkImage(
            imageUrl = user.imageUrl,
            modifier = Modifier
                .size(108.dp)
                .clip(CircleShape),
            contentDescription = null
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = role.name, // Display role name
            style = KnightsTheme.typography.labelSmallM,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
        Text(
            text = user.name,
            style = KnightsTheme.typography.titleMediumB,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = user.introduction,
            style = KnightsTheme.typography.titleSmallR140,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}

@Preview
@Composable
private fun ProjectDetailSpeakerPreview() {
    KnightsTheme {
        ProjectDetailSpeaker(
            user = FakeOpenKnightsData.fakeUsers.first(),
            role = ProjectRole.TEAM_LEADER
        )
    }
}