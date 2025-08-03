package com.openknights.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.openknights.app.core.designsystem.theme.KnightsTheme
import com.openknights.app.core.testing.FakeOpenKnightsData
import com.openknights.app.feature.contest.ContestListScreen
import com.openknights.app.feature.project.projectlist.ProjectListScreen
import com.openknights.app.feature.user.UserScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KnightsTheme {
                OpenKnightsApp()
            }
        }
    }
}

// --- Navigation 대상 정의
sealed interface ScreenEntry
data object ContestListScreenEntry : ScreenEntry
data class ProjectListScreenEntry(val term: String) : ScreenEntry
data object UserScreenEntry : ScreenEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenKnightsApp() {
    val backStack = remember { mutableStateListOf<ScreenEntry>(ContestListScreenEntry) }
    val currentEntry = backStack.lastOrNull()
    val latestContestTerm = FakeOpenKnightsData.fakeContests.firstOrNull()?.term ?: ""

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "OpenKnights",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    if (backStack.size > 1) {
                        IconButton(onClick = { backStack.removeLastOrNull() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentEntry is ContestListScreenEntry,
                    onClick = {
                        backStack.clear()
                        backStack.add(ContestListScreenEntry)
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("HOME") }
                )
                NavigationBarItem(
                    selected = currentEntry is ProjectListScreenEntry,
                    onClick = {
                        backStack.clear()
                        backStack.add(ProjectListScreenEntry(latestContestTerm))
                    },
                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
                    label = { Text("프로젝트") }
                )
                NavigationBarItem(
                    selected = currentEntry is UserScreenEntry,
                    onClick = {
                        backStack.clear()
                        backStack.add(UserScreenEntry)
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("사용자") }
                )
            }
        }
    ) { innerPadding ->
        when (val entry = currentEntry) {
            is ContestListScreenEntry ->
                ContestListScreen(
                    onContestClick = { contest ->
                        backStack.add(ProjectListScreenEntry(contest.term))
                    },
                    padding = innerPadding
                )
            is ProjectListScreenEntry ->
                ProjectListScreen(
                    contestTerm = entry.term,
                    onProjectClick = {},
                    onShowErrorSnackBar = {},
                )
            is UserScreenEntry ->
                UserScreen()
            null -> {
                // do nothing
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OpenKnightsAppPreview() {
    KnightsTheme {
        OpenKnightsApp()
    }
}
