package com.openknights.core.designsystem.preview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openknights.core.designsystem.component.BottomLogo
import com.openknights.core.designsystem.component.IconTextChip
import com.openknights.core.designsystem.component.KnightsCard
import com.openknights.core.designsystem.component.TextChip
import com.openknights.core.designsystem.theme.KnightsColor
import com.openknights.core.designsystem.theme.KnightsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesignSystemDemo() {
    var counter by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("OpenKnights : 오픈소스 경진대회") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { counter++ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text("+")
            }
        },
        bottomBar = {
            BottomLogo(text = "Open Knights 2025 하반기")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Compose Design System",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Clicked $counter times",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "OpenKnights 앱에서 Scaffold, FAB, BottomLogo 등을 사용하는 예시입니다.",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = { counter++ }) {
                Text("Click Me")
            }
            Spacer(modifier = Modifier.height(24.dp))
            KnightsCard {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "정보 아이콘"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("이것은 아이콘과 텍스트가 있는 Knights Card입니다.")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            IconTextChip(
                "북마크",
                containerColor = KnightsColor.DarkGray,
                labelColor = KnightsColor.White,
                icon = Icons.Filled.Star,
                iconTint = Color.Yellow,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row( modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextChip(
                    "카테고리",
                    containerColor = Color.Transparent,
                    labelColor = KnightsColor.LightGray,
                    border = BorderStroke(1.dp, KnightsColor.LightGray),
                )
                TextChip(
                    "Track 01",
                    containerColor = KnightsColor.Blue01,
                    labelColor = KnightsColor.White,
                )
                TextChip(
                    "16:45 발표",
                    containerColor = KnightsColor.Blue02A30,
                    labelColor = KnightsColor.Blue01,
                )
            }
        }
    }
}


// 미리보기 코드
@Preview(showBackground = true, name = "Light Theme")
@Composable
fun DesignSystemDemoPreviewLight() {
    KnightsTheme(useDarkTheme = false) {
        DesignSystemDemo()
    }
}

@Preview(showBackground = true, name = "Dark Theme")
@Composable
fun DesignSystemDemoPreviewDark() {
    KnightsTheme(useDarkTheme = true) {
        DesignSystemDemo()
    }
}

