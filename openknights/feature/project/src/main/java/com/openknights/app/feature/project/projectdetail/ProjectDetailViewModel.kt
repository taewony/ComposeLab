package com.openknights.app.feature.project.projectdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openknights.app.core.testing.FakeOpenKnightsData
import com.openknights.app.feature.project.projectdetail.model.ProjectDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ProjectDetailViewModel : ViewModel() {

    private val _projectUiState =
        MutableStateFlow<ProjectDetailUiState>(ProjectDetailUiState.Loading)
    val projectUiState = _projectUiState.asStateFlow()

    fun fetchProject(projectId: String) {
        viewModelScope.launch {
            // 실제 API 호출이나 DB 조회 로직이 들어갈 수 있음
            // 지금은 가짜 데이터를 만들어서 바로 전달
            val project = FakeOpenKnightsData.fakeProjects.firstOrNull { it.id == projectId }
            if (project != null) {
                _projectUiState.value = ProjectDetailUiState.Success(project)
            } else {
                _projectUiState.value = ProjectDetailUiState.Error("Project not found")
            }
        }
    }
}