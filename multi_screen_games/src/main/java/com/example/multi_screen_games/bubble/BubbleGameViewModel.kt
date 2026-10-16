package com.example.multi_screen_games.bubble

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.example.multi_screen_games.core.BaseGameViewModel
import com.example.multi_screen_games.core.launchFrameLoop
import kotlinx.coroutines.Job
import kotlin.random.Random

class BubbleGameViewModel : BaseGameViewModel<BubbleGameState, BubbleGameIntent>(
    initialState = BubbleGameState()
) {
    private var gameLoopJob: Job? = null

    // ── side-effect 전용 상태 (UiState 밖) ────────────────
    private var canvasWidth: Float = 0f
    private var canvasHeight: Float = 0f
    private var msSinceLastSpawn: Long = 0L
    private val random = Random.Default

    private val spawnIntervalMs = 800L
    private val bubbleLifetimeMs = 3_000L
    private val bubbleRadiusMin = 40f
    private val bubbleRadiusMax = 80f

    /** Canvas 크기를 알려주는 인프라 메서드 (UI의 onSizeChanged에서 호출) */
    fun updateCanvasSize(width: Float, height: Float) {
        canvasWidth = width
        canvasHeight = height
    }

    // ── 순수 리듀서 ────────────────────────────────────────
    override fun reduce(
        state: BubbleGameState,
        intent: BubbleGameIntent
    ): BubbleGameState = when (intent) {

        BubbleGameIntent.StartGame -> BubbleGameState(
            isRunning = true,
            nextBubbleId = 0L
        )

        BubbleGameIntent.GameOver -> state.copy(
            isRunning = false,
            isGameOver = true
        )

        is BubbleGameIntent.TapBubble -> {
            // 미존재 id 방어
            val target = state.bubbles.firstOrNull { it.id == intent.bubbleId }
                ?: return state
            state.copy(
                score = state.score + 10,
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
            val survived = state.bubbles
                .map { it.copy(remainingMillis = it.remainingMillis - intent.deltaMillis) }
                .filter { it.remainingMillis > 0L }
            state.copy(bubbles = survived)
        }
    }

    // ── 부수 효과 ─────────────────────────────────────────
    override fun handleSideEffects(
        intent: BubbleGameIntent,
        newState: BubbleGameState
    ) {
        when (intent) {
            BubbleGameIntent.StartGame -> {
                msSinceLastSpawn = 0L
                startGameLoop()
            }

            BubbleGameIntent.GameOver -> stopGameLoop()

            is BubbleGameIntent.Tick -> {
                if (!newState.isRunning) return

                // Spawn 타이밍 판단 (side-effect 영역)
                msSinceLastSpawn += intent.deltaMillis
                if (msSinceLastSpawn >= spawnIntervalMs &&
                    canvasWidth > 0f && canvasHeight > 0f
                ) {
                    msSinceLastSpawn = 0L
                    handleIntent(BubbleGameIntent.SpawnBubble(createRandomBubble(newState)))
                }
            }

            else -> Unit
        }
    }

    // ── 헬퍼 ──────────────────────────────────────────────
    private fun createRandomBubble(state: BubbleGameState): Bubble {
        val radius = bubbleRadiusMin + random.nextFloat() * (bubbleRadiusMax - bubbleRadiusMin)
        val margin = radius + 8f
        val x = margin + random.nextFloat() * (canvasWidth - 2 * margin).coerceAtLeast(1f)
        val y = margin + random.nextFloat() * (canvasHeight - 2 * margin).coerceAtLeast(1f)
        return Bubble(
            id = state.nextBubbleId,
            position = Offset(x, y),
            radius = radius,
            color = Color(
                red = random.nextFloat(),
                green = random.nextFloat(),
                blue = random.nextFloat(),
                alpha = 0.8f
            ),
            remainingMillis = bubbleLifetimeMs
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