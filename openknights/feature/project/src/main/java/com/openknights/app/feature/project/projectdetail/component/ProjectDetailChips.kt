package com.openknights.app.feature.project.projectdetail.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.openknights.app.feature.project.projectdetail.model.ProjectDetailDataProvider
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import com.openknights.app.core.ui.OutlineChip
import com.openknights.app.core.designsystem.theme.KnightsTheme
import com.openknights.app.core.model.Project

@Composable
internal fun ProjectDetailChips(project: Project) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlineChip(text = project.phase.label, borderColor = MaterialTheme.colorScheme.primary, textColor = MaterialTheme.colorScheme.onSurface)
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewProjectDetailChips(@PreviewParameter(ProjectDetailDataProvider::class) project: Project) {
    KnightsTheme {
        ProjectDetailChips(
            project = project,
        )
    }
}