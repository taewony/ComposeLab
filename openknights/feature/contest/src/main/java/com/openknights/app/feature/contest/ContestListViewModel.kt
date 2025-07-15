
package com.openknights.app.feature.contest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openknights.app.core.model.Contest
import com.openknights.app.core.testing.FakeOpenKnightsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContestListViewModel @Inject constructor(
    // TODO: 추후 ContestRepository 등으로 대체
) : ViewModel() {

    private val _uiState = MutableStateFlow<ContestListUiState>(ContestListUiState.Loading)
    val uiState: StateFlow<ContestListUiState> = _uiState.asStateFlow()

    init {
        fetchContests()
    }

    private fun fetchContests() {
        viewModelScope.launch {
            // TODO: 실제 데이터 로직 구현 (Repository 사용)
            _uiState.value = ContestListUiState.Success(
                contests = FakeOpenKnightsData.fakeContests.toImmutableList()
            )
        }
    }
}

sealed interface ContestListUiState {
    object Loading : ContestListUiState
    data class Success(val contests: ImmutableList<Contest>) : ContestListUiState
    // TODO: Error 상태 추가
}
