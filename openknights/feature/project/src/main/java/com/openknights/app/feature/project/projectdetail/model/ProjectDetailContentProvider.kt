package com.openknights.app.feature.project.projectdetail.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.openknights.app.core.model.Project
import com.openknights.app.core.testing.FakeOpenKnightsData

class ProjectDetailContentProvider : PreviewParameterProvider<Project> {
    override val values: Sequence<Project> = sequenceOf(
        FakeOpenKnightsData.fakeProjects.first()
    )
}
