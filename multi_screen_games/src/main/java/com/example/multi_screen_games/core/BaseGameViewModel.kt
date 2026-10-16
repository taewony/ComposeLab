package com.example.multi_screen_games.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseGameViewModel<S : UiState, I : UiIntent>(
    initialState: S
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    /** 단방향 흐름의 유일한 진입점 */
    fun handleIntent(intent: I) {
        val newState = reduce(_uiState.value, intent)
        _uiState.value = newState
        handleSideEffects(intent, newState)
    }

    /** 순수 함수: (State, Intent) → State */
    protected abstract fun reduce(state: S, intent: I): S

    /** 부수 효과: 코루틴 시작/중단, 외부 콜백 등 */
    protected open fun handleSideEffects(intent: I, newState: S) {}

    override fun onCleared() {
        super.onCleared()
        // viewModelScope는 자동 취소되지만, 필요 시 여기서 정리
    }
}