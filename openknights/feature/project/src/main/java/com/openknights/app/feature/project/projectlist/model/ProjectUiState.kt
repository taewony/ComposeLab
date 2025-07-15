package com.openknights.app.feature.project.projectlist.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.openknights.app.core.model.Project
import com.openknights.app.core.model.ProjectPhase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
sealed interface ProjectUiState {

    @Immutable
    data object Loading : ProjectUiState

    @Immutable
    data class Projects(
        val groups: ImmutableList<Group> = persistentListOf(),
    ) : ProjectUiState {
        @Immutable
        data class Group(
            val projectPhase: ProjectPhase,
            val projects: ImmutableList<Project>,
        )
    }
}