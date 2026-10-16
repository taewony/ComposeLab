package com.example.multi_screen_games.bubble

import androidx.lifecycle.viewModelScope
import com.example.multi_screen_games.core.BaseGameViewModel
import com.example.multi_screen_games.core.launchFrameLoop
import kotlinx.coroutines.Job
import kotlin.random.Random

class BubbleGameViewModel : BaseGameViewModel<BubbleGameState, BubbleGameIntent>(
    initialState = BubbleGameState()
) {
    private var gameLoopJob: Job? = null
    private val random = Random(System.currentTimeMillis())
    private var msSinceLastSpawn = 0L
    private val spawnIntervalMs = 900L   // 튜닝 가능

    override fun reduce(state: BubbleGameState, intent: BubbleGameIntent): BubbleGameState =
        when (intent) {
            BubbleGameIntent.StartGame -> BubbleGameState(
                isRunning = true, nextBubbleId = 0L
            )

            BubbleGameIntent.GameOver ->
                state.copy(isGameOver = true, isRunning = false)

            is BubbleGameIntent.TapBubble -> {
                // ★ 존재하지 않는 버블 중복 터치 방어
                val target = state.bubbles.firstOrNull { it.id == intent.bubbleId }
                    ?: return state
                state.copy(
                    score = state.score + 1,
                    bubbles = state.bubbles - target
                )
            }

            is BubbleGameIntent.SpawnBubble -> {
                if (!state.isRunning) return state
                state.copy(
                    bubbles = state.bubbles + intent.bubble,
                    nextBubbleId = state.nextBubbleId + 1
                )
            }

            is BubbleGameIntent.Tick -> {
                if (!state.isRunning) return state
                val dtSec = intent.deltaMillis / 1000f
                val moved = state.bubbles.map {
                    it.copy(y = it.y + it.speed * dtSec)
                }
                // 화면 하단 완전 이탈(y - radius > 1f) 판정
                val escaped = moved.any { it.y - it.radius > 1f }
                val survivors = moved.filter { it.y - it.radius <= 1f }

                if (escaped) state.copy(
                    bubbles = survivors,
                    isRunning = false,
                    isGameOver = true
                ) else state.copy(bubbles = survivors)
            }
        }

    override fun handleSideEffects(intent: BubbleGameIntent, newState: BubbleGameState) {
        when (intent) {
            BubbleGameIntent.StartGame -> {
                msSinceLastSpawn = 0L
                startGameLoop()
            }
            is BubbleGameIntent.Tick -> {
                if (!newState.isRunning && !newState.isGameOver) return
                if (newState.isGameOver) { stopGameLoop(); return }

                // Spawn 결정 (side-effect 영역)
                msSinceLastSpawn += intent.deltaMillis
                if (msSinceLastSpawn >= spawnIntervalMs) {
                    msSinceLastSpawn = 0L
                    handleIntent(BubbleGameIntent.SpawnBubble(createRandomBubble(newState)))
                }
            }
            BubbleGameIntent.GameOver,
            is BubbleGameIntent.SpawnBubble,
            is BubbleGameIntent.TapBubble -> Unit
        }
    }

    private fun createRandomBubble(state: BubbleGameState): Bubble {
        val radius = 0.06f + random.nextFloat() * 0.04f     // 0.06~0.10
        val x = radius + random.nextFloat() * (1f - 2 * radius) // 벽에 안 붙게
        val speed = 0.25f + random.nextFloat() * 0.25f      // 0.25~0.50 /초
        return Bubble(
            id = state.nextBubbleId,
            x = x,
            y = -radius,                                    // 화면 위에서 시작
            radius = radius,
            speed = speed
        )
    }

    private fun startGameLoop() {
        if (gameLoopJob?.isActive == true) return
        gameLoopJob = viewModelScope.launchFrameLoop { dt ->
            handleIntent(BubbleGameIntent.Tick(dt))
        }
    }

    private fun stopGameLoop() {
        gameLoopJob?.cancel()
        gameLoopJob = null
    }
}