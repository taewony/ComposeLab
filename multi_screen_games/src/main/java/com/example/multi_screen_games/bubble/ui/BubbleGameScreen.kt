package com.example.multi_screen_games.bubble.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.multi_screen_games.bubble.Bubble
import com.example.multi_screen_games.bubble.BubbleGameIntent
import com.example.multi_screen_games.bubble.BubbleGameState
import com.example.multi_screen_games.bubble.BubbleGameViewModel
import com.example.multi_screen_games.ui.theme.ComposeLabTheme   // 실제 테마 이름으로 교체

/* ══════════════════════════════════════════════════════════
 *  1. Stateful Wrapper — MainActivity의 entry가 호출
 * ══════════════════════════════════════════════════════════ */

@Composable
fun BubbleGameScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BubbleGameViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BubbleGameScreenContent(
        uiState = uiState,
        onStartGame = { viewModel.handleIntent(BubbleGameIntent.StartGame) },
        onTapBubble = { id -> viewModel.handleIntent(BubbleGameIntent.TapBubble(id)) },
        onCanvasSizeChanged = viewModel::updateCanvasSize,
        onBack = onBack,
        modifier = modifier
    )
}

/* ══════════════════════════════════════════════════════════
 *  2. Stateless Content — 순수 UI, Preview/테스트 대상
 * ══════════════════════════════════════════════════════════ */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BubbleGameScreenContent(
    uiState: BubbleGameState,
    onStartGame: () -> Unit,
    onTapBubble: (Long) -> Unit,
    onCanvasSizeChanged: (width: Float, height: Float) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("버블 터뜨리기 · Score ${uiState.score}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로 가기"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            BubbleCanvas(
                bubbles = uiState.bubbles,
                onTapBubble = onTapBubble,
                onCanvasSizeChanged = onCanvasSizeChanged
            )

            // 시작/재시작 오버레이
            if (!uiState.isRunning) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = onStartGame) {
                        Text(if (uiState.isGameOver) "다시 시작" else "시작")
                    }
                }
            }
        }
    }
}

/* ──────────────────────────────────────────────────────────
 *  Canvas — 렌더링 + Hit Test
 * ────────────────────────────────────────────────────────── */

@Composable
private fun BubbleCanvas(
    bubbles: List<Bubble>,
    onTapBubble: (Long) -> Unit,
    onCanvasSizeChanged: (Float, Float) -> Unit,
    modifier: Modifier = Modifier
) {
    // ★ 최신 버블 리스트를 pointerInput 람다 안에서 안전하게 참조
    val currentBubbles by rememberUpdatedState(bubbles)
    val currentOnTap by rememberUpdatedState(onTapBubble)

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { size ->
                onCanvasSizeChanged(size.width.toFloat(), size.height.toFloat())
            }
            // key = Unit: 제스처 감지기를 재시작하지 않고 최신 상태만 참조
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val hit = currentBubbles.asReversed().firstOrNull { b ->
                        val dx = offset.x - b.position.x
                        val dy = offset.y - b.position.y
                        dx * dx + dy * dy <= b.radius * b.radius
                    }
                    hit?.let { currentOnTap(it.id) }
                }
            }
    ) {
        currentBubbles.forEach { bubble ->
            drawCircle(
                color = bubble.color,
                radius = bubble.radius,
                center = bubble.position
            )
        }
    }
}

/* ──────────────────────────────────────────────────────────
 *  Preview — Content 직접 호출
 * ────────────────────────────────────────────────────────── */

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BubbleGameScreenContent_IdlePreview() {
    ComposeLabTheme {
        BubbleGameScreenContent(
            uiState = BubbleGameState(),
            onStartGame = {},
            onTapBubble = {},
            onCanvasSizeChanged = { _, _ -> },
            onBack = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BubbleGameScreenContent_RunningPreview() {
    val sample = listOf(
        Bubble(
            id = 0L,
            position = androidx.compose.ui.geometry.Offset(120f, 200f),
            radius = 60f,
            color = androidx.compose.ui.graphics.Color(0xCC4FC3F7),
            remainingMillis = 2_400L
        ),
        Bubble(
            id = 1L,
            position = androidx.compose.ui.geometry.Offset(280f, 480f),
            radius = 50f,
            color = androidx.compose.ui.graphics.Color(0xCCFF8A65),
            remainingMillis = 1_200L
        )
    )
    ComposeLabTheme {
        BubbleGameScreenContent(
            uiState = BubbleGameState(
                score = 30,
                bubbles = sample,
                isRunning = true,
                nextBubbleId = 2L
            ),
            onStartGame = {},
            onTapBubble = {},
            onCanvasSizeChanged = { _, _ -> },
            onBack = {}
        )
    }
}