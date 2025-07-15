package com.openknights.app.feature.project.projectdetail.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.openknights.app.core.model.Project

@Stable
sealed interface ProjectDetailUiState {

    @Immutable
    data object Loading : ProjectDetailUiState

    @Immutable
    data class Success(val project: Project) : ProjectDetailUiState
}