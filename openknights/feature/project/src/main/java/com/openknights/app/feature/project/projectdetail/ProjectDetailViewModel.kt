package com.openknights.app.feature.project.projectdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openknights.app.core.model.Project
import com.openknights.app.core.testing.FakeOpenKnightsData
import com.openknights.app.feature.project.projectdetail.model.ProjectDetailUiState
import com.openknights.app.core.router.api.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectDetailViewModel @Inject constructor(
    private val navigator: Navigator,
) : ViewModel() {

    private val _projectUiState =
        MutableStateFlow<ProjectDetailUiState>(ProjectDetailUiState.Loading)
    val projectUiState = _projectUiState.asStateFlow()

    fun fetchProject(projectId: String) {
        viewModelScope.launch {
            val project = FakeOpenKnightsData.fakeProjects.first { it.id == projectId }
            _projectUiState.value = ProjectDetailUiState.Success(project)
        }
    }

    fun navigateBack() = viewModelScope.launch {
        navigator.navigateBack()
    }
}