package com.example.multi_screen_games.stopwatch

import androidx.lifecycle.viewModelScope
import com.example.multi_screen_games.core.BaseGameViewModel
import com.example.multi_screen_games.core.launchFrameLoop
import com.example.multi_screen_games.stopwatch.ui.StopwatchIntent
import com.example.multi_screen_games.stopwatch.ui.StopwatchState
import kotlinx.coroutines.Job

class StopwatchViewModel : BaseGameViewModel<StopwatchState, StopwatchIntent>(
    initialState = StopwatchState()
) {
    private var timerJob: Job? = null

    override fun reduce(state: StopwatchState, intent: StopwatchIntent): StopwatchState =
        when (intent) {
            StopwatchIntent.StartClick ->
                if (state.isRunning) state
                else state.copy(isRunning = true)

            StopwatchIntent.StopClick ->
                if (!state.isRunning) state
                else state.copy(isRunning = false)

            StopwatchIntent.ResetClick ->
                StopwatchState()   // 완전 초기화

            StopwatchIntent.LapClick ->
                // 실행 중일 때만 랩타임 추가 (정지 상태에서는 무시)
                if (!state.isRunning) state
                else state.copy(lapTimes = state.lapTimes + state.elapsedMillis)

            is StopwatchIntent.Tick ->
                // ★ 정지 상태에서는 시간이 흐르면 안 됨
                if (!state.isRunning) state
                else state.copy(elapsedMillis = state.elapsedMillis + intent.deltaMillis)
        }

    override fun handleSideEffects(intent: StopwatchIntent, newState: StopwatchState) {
        when (intent) {
            StopwatchIntent.StartClick -> startTimerIfNeeded()
            StopwatchIntent.StopClick,
            StopwatchIntent.ResetClick  -> stopTimer()
            else -> Unit
        }
    }

    private fun startTimerIfNeeded() {
        if (timerJob?.isActive == true) return
        timerJob = viewModelScope.launchFrameLoop { deltaMillis ->
            handleIntent(StopwatchIntent.Tick(deltaMillis))
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }
}