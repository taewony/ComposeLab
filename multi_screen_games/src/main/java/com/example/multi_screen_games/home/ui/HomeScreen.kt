package com.example.multi_screen_games.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.BubbleChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * 앱의 초기 화면(메뉴). 두 게임으로의 진입점을 제공합니다.
 *
 * @param onNavigateToStopwatch 스톱워치 게임으로 이동
 * @param onNavigateToBubble   버블 게임으로 이동
 */
@Composable
fun HomeScreen(
    onNavigateToStopwatch: () -> Unit,
    onNavigateToBubble: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ── 헤더 ──────────────────────────────────────
            Text(
                text = "미니게임즈",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "플레이할 게임을 선택하세요",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(48.dp))

            // ── 게임 선택 카드 ─────────────────────────────
            GameMenuCard(
                title = "스톱워치",
                description = "정밀한 시간 측정과 랩타임 기록",
                icon = Icons.Default.AccessTime,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                onClick = onNavigateToStopwatch
            )

            Spacer(Modifier.height(16.dp))

            GameMenuCard(
                title = "버블 터뜨리기",
                description = "떠오르는 버블을 터뜨려 점수를 획득하세요",
                icon = Icons.Default.BubbleChart,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                onClick = onNavigateToBubble
            )
        }
    }
}

/**
 * 재사용 가능한 게임 메뉴 카드.
 * 새로운 게임을 추가할 때 이 카드를 하나 더 배치하면 됩니다.
 */
@Composable
private fun GameMenuCard(
    title: String,
    description: String,
    icon: ImageVector,
    containerColor: androidx.compose.ui.graphics.Color,
    contentColor: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = contentColor.copy(alpha = 0.75f)
                )
            }
        }
    }
}

// ── Preview ─────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(
            onNavigateToStopwatch = {},
            onNavigateToBubble = {}
        )
    }
}