package com.example.multi_screen_games.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// ── 도메인 모델: 새 구조에서는 stopwatch/ 패키지에 위치 ──
import com.example.multi_screen_games.stopwatch.GameConfig
import com.example.multi_screen_games.stopwatch.GameData
import com.example.multi_screen_games.stopwatch.GamePhase
import com.example.multi_screen_games.stopwatch.GameUiState
import com.example.multi_screen_games.stopwatch.UserData
// ── 테마: 프로젝트의 실제 테마 이름으로 교체하세요 (예: ComposeLabTheme) ──
import com.example.multi_screen_games.ui.theme.ComposeLabTheme

/* ──────────────────────────────────────────────────────────
 *  메인 화면 (Stateless)
 *  - Nav3의 entryProvider에서 uiState + 콜백을 주입
 *  - onBack은 backStack.removeLastOrNull()로 연결
 * ────────────────────────────────────────────────────────── */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StopwatchScreen(
    uiState: GameUiState,
    onStart: () -> Unit,
    onStop: () -> Unit,
    onNextRound: () -> Unit,
    onReset: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("스톱워치 게임") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ── 상단: 목표 시간 + 라운드 진행 ────────────
            TargetHeader(uiState = uiState)

            Spacer(Modifier.height(24.dp))

            // ── 중앙: 스톱워치 ─────────────────────────
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                StopwatchDisplay(
                    elapsedMillis = uiState.elapsedMillis,
                    targetMillis = uiState.config.targetMillis,
                    phase = uiState.phase
                )

                if (uiState.phase == GamePhase.RESULT) {
                    uiState.lastResult?.let { result ->
                        ResultOverlay(result = result)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── 하단: 세션 요약 + 컨트롤 ─────────────────
            SessionSummary(uiState = uiState)

            Spacer(Modifier.height(16.dp))

            ControlButtons(
                phase = uiState.phase,
                onStart = onStart,
                onStop = onStop,
                onNextRound = onNextRound,
                onReset = onReset
            )
        }
    }
}

/* ──────────────────────────────────────────────────────────
 *  이하 컴포넌트는 원본 그대로 유지 (변경 없음)
 * ────────────────────────────────────────────────────────── */

@Composable
private fun TargetHeader(
    uiState: GameUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "TARGET",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = formatMillis(uiState.config.targetMillis),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "허용 오차 ±${uiState.config.toleranceMillis}ms",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (uiState.phase != GamePhase.IDLE) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Round ${uiState.currentRound.coerceAtMost(uiState.config.maxRounds)}"
                        + " / ${uiState.config.maxRounds}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun StopwatchDisplay(
    elapsedMillis: Long,
    targetMillis: Long,
    phase: GamePhase,
    modifier: Modifier = Modifier
) {
    val isOverTarget = elapsedMillis > targetMillis

    val containerColor = when {
        phase != GamePhase.RUNNING -> MaterialTheme.colorScheme.surfaceVariant
        isOverTarget -> MaterialTheme.colorScheme.errorContainer
        else -> MaterialTheme.colorScheme.primaryContainer
    }

    val textColor = when {
        phase != GamePhase.RUNNING -> MaterialTheme.colorScheme.onSurfaceVariant
        isOverTarget -> MaterialTheme.colorScheme.onErrorContainer
        else -> MaterialTheme.colorScheme.onPrimaryContainer
    }

    Card(
        modifier = modifier.size(240.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = formatMillis(elapsedMillis),
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = "seconds",
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor
                )
            }
        }
    }
}

@Composable
private fun ResultOverlay(
    result: GameData,
    modifier: Modifier = Modifier
) {
    val accent = if (result.isSuccess) Color(0xFF4CAF50)
    else MaterialTheme.colorScheme.error
    val title = if (result.isSuccess) "SUCCESS" else "MISS"
    val emoji = when {
        !result.isSuccess -> "❌"
        result.deviation == 0L -> "⭐"
        result.deviation <= result.toleranceMillis / 3 -> "✨"
        else -> "👍"
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$emoji  $title",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = accent
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "${formatMillis(result.stoppedMillis)} " +
                        "(${signOf(result.deviation)}${result.deviation}ms)",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "+${result.score}점",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = accent
            )
        }
    }
}

@Composable
private fun SessionSummary(
    uiState: GameUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.roundResults.isNotEmpty()) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                uiState.roundResults.forEach { r ->
                    RoundDot(isSuccess = r.isSuccess)
                }
            }
            Spacer(Modifier.height(12.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StatChip(
                modifier = Modifier.weight(1f),
                label = "세션 점수",
                value = "${uiState.sessionScore}"
            )
            StatChip(
                modifier = Modifier.weight(1f),
                label = "성공률",
                value = "${(uiState.sessionSuccessRate * 100).toInt()}%"
            )
            StatChip(
                modifier = Modifier.weight(1f),
                label = "최고 점수",
                value = "${uiState.userData.bestScore}"
            )
        }
    }
}

@Composable
private fun RoundDot(
    isSuccess: Boolean,
    modifier: Modifier = Modifier
) {
    val color = if (isSuccess) Color(0xFF4CAF50)
    else MaterialTheme.colorScheme.error
    Box(
        modifier = modifier.size(12.dp)
    ) {
        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = color),
            modifier = Modifier.fillMaxSize()
        ) {}
    }
}

@Composable
private fun StatChip(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ControlButtons(
    phase: GamePhase,
    onStart: () -> Unit,
    onStop: () -> Unit,
    onNextRound: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        when (phase) {
            GamePhase.IDLE, GamePhase.FINISHED -> {
                Button(
                    onClick = onStart,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (phase == GamePhase.FINISHED) "다시 도전" else "시작")
                }
            }
            GamePhase.RUNNING -> {
                Button(
                    onClick = onStop,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text("정지!")
                }
            }
            GamePhase.RESULT -> {
                Button(
                    onClick = onNextRound,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("다음 라운드")
                }
            }
        }

        OutlinedButton(
            onClick = onReset,
            enabled = phase != GamePhase.RUNNING
        ) {
            Text("초기화")
        }
    }
}

/* ──────────────────────────────────────────────────────────
 *  유틸
 * ────────────────────────────────────────────────────────── */

/** ms → "S.mmm" 형식 (예: 3.042) */
fun formatMillis(millis: Long): String {
    val seconds = millis / 1000
    val ms = millis % 1000
    return "%d.%03d".format(seconds, ms)
}

private fun signOf(value: Long): String = if (value >= 0) "+" else "-"

/* ──────────────────────────────────────────────────────────
 *  Preview
 * ────────────────────────────────────────────────────────── */

@Preview(showBackground = true, heightDp = 780)
@Composable
private fun StopwatchScreen_IdlePreview() {
    ComposeLabTheme {
        StopwatchScreen(
            uiState = GameUiState(),
            onStart = {}, onStop = {}, onNextRound = {}, onReset = {},
            onBack = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 780)
@Composable
private fun StopwatchScreen_RunningPreview() {
    ComposeLabTheme {
        StopwatchScreen(
            uiState = GameUiState(
                phase = GamePhase.RUNNING,
                config = GameConfig(targetMillis = 3000, toleranceMillis = 100, maxRounds = 5),
                elapsedMillis = 2450,
                currentRound = 2
            ),
            onStart = {}, onStop = {}, onNextRound = {}, onReset = {},
            onBack = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 780)
@Composable
private fun StopwatchScreen_ResultPreview() {
    val sampleResult = GameData(
        round = 2,
        targetMillis = 3000,
        stoppedMillis = 3012,
        toleranceMillis = 100
    )
    ComposeLabTheme {
        StopwatchScreen(
            uiState = GameUiState(
                phase = GamePhase.RESULT,
                config = GameConfig(),
                userData = UserData(
                    bestScore = 100, currentScore = 210,
                    totalGames = 12, successGames = 9
                ),
                elapsedMillis = 3012,
                currentRound = 2,
                roundResults = listOf(
                    GameData(1, 3000, 3005, 100),
                    sampleResult
                )
            ),
            onStart = {}, onStop = {}, onNextRound = {}, onReset = {},
            onBack = {}
        )
    }
}