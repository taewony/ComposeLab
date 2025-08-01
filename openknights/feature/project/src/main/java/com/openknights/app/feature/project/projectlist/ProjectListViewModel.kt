package com.openknights.app.feature.project.projectlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openknights.app.core.model.Project
import com.openknights.app.core.model.ProjectPhase
import com.openknights.app.core.testing.FakeOpenKnightsData
import com.openknights.app.feature.project.projectlist.model.ProjectUiState
import com.openknights.app.feature.project.projectnavigation.PROJECT_DETAIL_ROUTE
import com.openknights.app.core.navigator.api.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(
    private val navigator: Navigator,
) : ViewModel() {

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState = MutableStateFlow<ProjectUiState>(ProjectUiState.Loading)
    val uiState = _uiState.asStateFlow()

    // TODO: contestTerm을 주입받도록 변경
    fun fetchProjects(contestTerm: String) {
        viewModelScope.launch {
            flow {
                // TODO: 실제 Repository에서 데이터 가져오도록 변경
                val projects = FakeOpenKnightsData.fakeProjects
                    .filter { it.contestTerm == contestTerm }
                    .groupBy { it.phase }
                    .map { (phase, projects) ->
                        ProjectUiState.Projects.Group(phase, projects.toPersistentList())
                    }
                    .toPersistentList()
                emit(ProjectUiState.Projects(projects))
            }
                .catch { throwable ->
                    _errorFlow.emit(throwable)
                }
                .onEach { combinedUiState ->
                    _uiState.value = combinedUiState
                }
                .launchIn(viewModelScope)
        }
    }

    fun navigateProjectDetail(projectId: String) = viewModelScope.launch {
        navigator.navigate("$PROJECT_DETAIL_ROUTE/$projectId")
    }

    fun navigateBack() = viewModelScope.launch {
        navigator.navigateBack()
    }
}