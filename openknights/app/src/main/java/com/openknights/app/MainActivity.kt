package com.openknights.app

// Navigation 3 전용 (예: androidx.navigation:navigation-compose:3.x.x)

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.NavEntry
import androidx.navigation3.NavDisplay
import androidx.navigation3.Scene
import androidx.navigation3.backStack
import androidx.navigation3.rememberNavController
import com.openknights.app.core.designsystem.theme.KnightsTheme
import com.openknights.app.core.testing.FakeOpenKnightsData
import dagger.hilt.android.AndroidEntryPoint

/**
 * Module: app - 앱의 메인 진입점 및 최상위 UI 구조를 정의합니다.
 */
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

// ✅ Navigation 3 Entry Definitions
sealed interface ScreenEntry : NavEntry

data object ContestListScreen : ScreenEntry
data class ProjectListScreen(val term: String) : ScreenEntry
data object UserScreen : ScreenEntry

// Screen: TopBar, BottomBar 및 Content를 보여주는 첫 화면
// ✅ Main Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenKnightsApp() {
    val navController = rememberNavController<ScreenEntry>()
    val backStack by navController.backStack.collectAsState()
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
                        IconButton(onClick = { navController.pop() }) {
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
                    selected = currentEntry is ContestListScreen,
                    onClick = {
                        navController.popUpToRoot()
                        navController.push(ContestListScreen)
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("HOME") }
                )
                NavigationBarItem(
                    selected = currentEntry is ProjectListScreen,
                    onClick = {
                        navController.popUpToRoot()
                        navController.push(ProjectListScreen(latestContestTerm))
                    },
                    icon = { Icon(Icons.Default.List, contentDescription = null) },
                    label = { Text("프로젝트") }
                )
                NavigationBarItem(
                    selected = currentEntry is UserScreen,
                    onClick = {
                        navController.popUpToRoot()
                        navController.push(UserScreen)
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("사용자") }
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            controller = navController,
            modifier = Modifier.padding(innerPadding)
        ) {
            Scene<ContestListScreen> {
                ContestListScreenView(
                    onContestClick = { contest ->
                        navController.push(ProjectListScreen(contest.term))
                    }
                )
            }

            Scene<ProjectListScreen> { entry ->
                ProjectListScreenView(term = entry.term)
            }

            Scene<UserScreen> {
                UserScreenView()
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
