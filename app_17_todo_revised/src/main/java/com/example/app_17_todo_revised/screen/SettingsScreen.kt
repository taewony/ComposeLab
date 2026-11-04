package com.example.app_17_todo_revised.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_17_todo_revised.data.settings.SortBy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentSort: SortBy,
    onSortChange: (SortBy) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "정렬 기준 설정",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // By Time (최신순) 옵션
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (currentSort == SortBy.TIME),
                        onClick = { onSortChange(SortBy.TIME) }
                    )
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (currentSort == SortBy.TIME),
                    onClick = { onSortChange(SortBy.TIME) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "생성 시각 순 (최신순)", fontSize = 16.sp)
            }

            // By Text (이름순) 옵션
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (currentSort == SortBy.TEXT),
                        onClick = { onSortChange(SortBy.TEXT) }
                    )
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (currentSort == SortBy.TEXT),
                    onClick = { onSortChange(SortBy.TEXT) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "텍스트 이름순 (가나다순)", fontSize = 16.sp)
            }
        }
    }
}