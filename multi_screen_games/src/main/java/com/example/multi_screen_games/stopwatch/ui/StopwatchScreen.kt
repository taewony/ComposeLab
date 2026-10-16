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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.multi_screen_games.stopwatch.StopwatchViewModel
import com.example.multi_screen_games.stopwatch.ui.StopwatchIntent
import com.example.multi_screen_games.ui.theme.ComposeLabTheme   // 실제 테마 이름으로 교체
import com.example.multi_screen_games.stopwatch.ui.StopwatchState

/* ══════════════════════════════════════════════════════════
 *  1. Stateful Wrapper — MainActivity의 entry가 호출하는 함수
 *     onBack만 받고, 나머지는 ViewModel과 연결
 * ══════════════════════════════════════════════════════════ */

@Composable
fun StopwatchScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StopwatchViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StopwatchScreenContent(
        uiState = uiState,
        onStart = { viewModel.handleIntent(StopwatchIntent.StartClick) },
        onStop  = { viewModel.handleIntent(StopwatchIntent.StopClick) },
        onReset = { viewModel.handleIntent(StopwatchIntent.ResetClick) },
        onLap   = { viewModel.handleIntent(StopwatchIntent.LapClick) },
        onBack  = onBack,
        modifier = modifier
    )
}

/* ══════════════════════════════════════════════════════════
 *  2. Stateless Content — 순수 UI, Preview/테스트 대상
 * ══════════════════════════════════════════════════════════ */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StopwatchScreenContent(
    uiState: StopwatchState,
    onStart: () -> Unit,
    onStop: () -> Unit,
    onReset: () -> Unit,
    onLap: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("스톱워치") },
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
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                StopwatchDisplay(
                    elapsedMillis = uiState.elapsedMillis,
                    isRunning = uiState.isRunning
                )
            }

            Spacer(Modifier.height(20.dp))

            ControlButtons(
                isRunning = uiState.isRunning,
                onStart = onStart,
                onStop = onStop,
                onReset = onReset,
                onLap = onLap
            )

            Spacer(Modifier.height(16.dp))

            if (uiState.lapTimes.isNotEmpty()) {
                LapList(lapTimes = uiState.lapTimes)
            }
        }
    }
}

/* ──────────────────────────────────────────────────────────
 *  이하 private 컴포넌트 — 이전 답변과 동일
 * ────────────────────────────────────────────────────────── */

@Composable
private fun StopwatchDisplay(
    elapsedMillis: Long,
    isRunning: Boolean,
    modifier: Modifier = Modifier
) {
    val containerColor = if (isRunning) MaterialTheme.colorScheme.primaryContainer
    else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (isRunning) MaterialTheme.colorScheme.onPrimaryContainer
    else MaterialTheme.colorScheme.onSurfaceVariant

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
                    text = formatElapsed(elapsedMillis),
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = if (isRunning) "RUNNING" else "PAUSED",
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor
                )
            }
        }
    }
}

@Composable
private fun ControlButtons(
    isRunning: Boolean,
    onStart: () -> Unit,
    onStop: () -> Unit,
    onReset: () -> Unit,
    onLap: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Button(
            onClick = if (isRunning) onStop else onStart,
            modifier = Modifier.weight(1f),
            colors = if (isRunning) ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ) else ButtonDefaults.buttonColors()
        ) {
            Text(if (isRunning) "정지" else "시작")
        }

        OutlinedButton(
            onClick = onLap,
            enabled = isRunning
        ) {
            Text("랩")
        }

        OutlinedButton(
            onClick = onReset,
            enabled = !isRunning
        ) {
            Text("초기화")
        }
    }
}

@Composable
private fun LapList(
    lapTimes: List<Long>,
    modifier: Modifier = Modifier
) {
    val reversed = lapTimes.asReversed()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        itemsIndexed(reversed) { idx, time ->
            val lapNo = lapTimes.size - idx
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Lap $lapNo",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = formatElapsed(time),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

/* ──────────────────────────────────────────────────────────
 *  유틸
 * ────────────────────────────────────────────────────────── */

/** ms → "MM:SS.CC" 형식 (예: 01:23.45) */
fun formatElapsed(millis: Long): String {
    val totalCs = millis / 10
    val cs = totalCs % 100
    val totalSec = totalCs / 100
    val sec = totalSec % 60
    val min = totalSec / 60
    return "%02d:%02d.%02d".format(min, sec, cs)
}

/* ──────────────────────────────────────────────────────────
 *  Preview — Content를 직접 호출
 * ────────────────────────────────────────────────────────── */

@Preview(showBackground = true, heightDp = 780)
@Composable
private fun StopwatchScreenContent_IdlePreview() {
    ComposeLabTheme {
        StopwatchScreenContent(
            uiState = StopwatchState(),
            onStart = {}, onStop = {}, onReset = {}, onLap = {}, onBack = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 780)
@Composable
private fun StopwatchScreenContent_RunningPreview() {
    ComposeLabTheme {
        StopwatchScreenContent(
            uiState = StopwatchState(
                elapsedMillis = 83_450L,
                isRunning = true,
                lapTimes = listOf(12_340L, 28_560L, 42_990L)
            ),
            onStart = {}, onStop = {}, onReset = {}, onLap = {}, onBack = {}
        )
    }
}