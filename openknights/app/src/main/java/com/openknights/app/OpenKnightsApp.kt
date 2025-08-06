package com.openknights.app

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.openknights.app.core.designsystem.theme.KnightsTheme
import com.openknights.app.core.testing.FakeOpenKnightsData
import com.openknights.app.feature.contest.ContestListScreen
import com.openknights.app.feature.project.projectdetail.ProjectDetailScreen
import com.openknights.app.feature.project.projectlist.ProjectListScreen
import com.openknights.app.feature.user.UserScreen
import com.openknights.feature.auth.AuthViewModel
import com.openknights.feature.auth.LoginScreen
import com.openknights.feature.auth.RegisterScreen
import com.openknights.feature.notice.NoticeScreen

// --- Navigation 대상 정의
sealed interface ScreenEntry
data object ContestListScreenEntry : ScreenEntry
data class ProjectListScreenEntry(val term: String) : ScreenEntry
data class ProjectDetailScreenEntry(val projectId: String) : ScreenEntry
data object UserScreenEntry : ScreenEntry
data class NoticeScreenEntry(val isLoggedIn: Boolean) : ScreenEntry
data object RegisterScreenEntry : ScreenEntry
data object LoginScreenEntry : ScreenEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenKnightsApp() {
    val backStack = remember { mutableStateListOf<ScreenEntry>(ContestListScreenEntry) }
    val currentEntry = backStack.lastOrNull()
    val latestContestTerm = FakeOpenKnightsData.fakeContests.firstOrNull()?.term ?: ""
    var showMenu by remember { mutableStateOf(false) }
    val authViewModel: AuthViewModel = viewModel()
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    Log.d("OpenKnightsApp", "isLoggedIn: $isLoggedIn")

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
                    } else {
                        Box {
                            IconButton(onClick = { showMenu = true }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu"
                                )
                            }
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("사용자 등록") },
                                    onClick = { 
                                        showMenu = false
                                        backStack.add(RegisterScreenEntry)
                                    }
                                )
                                if (isLoggedIn) {
                                    DropdownMenuItem(
                                        text = { Text("로그아웃") },
                                        onClick = {
                                            showMenu = false
                                            authViewModel.signOut()
                                            backStack.clear()
                                            backStack.add(LoginScreenEntry)
                                        }
                                    )
                                } else {
                                    DropdownMenuItem(
                                        text = { Text("로그인") },
                                        onClick = {
                                            showMenu = false
                                            backStack.add(LoginScreenEntry)
                                        }
                                    )
                                }
                                DropdownMenuItem(
                                    text = { Text("프로젝트 등록") },
                                    onClick = { 
                                        showMenu = false
                                        // TODO: 프로젝트 등록 화면으로 이동 로직 추가
                                    }
                                )
                            }
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = { backStack.add(NoticeScreenEntry(isLoggedIn)) }
                    ) {
                        // 1. 로그인 상태에 따라 '모양' 결정
                        val imageVector = if (isLoggedIn) {
                            Icons.Default.Notifications
                        } else {
                            Icons.Outlined.Notifications
                        }

                        // 2. 로그인 상태에 따라 '색상(tint)' 결정
                        val tint = if (isLoggedIn) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        }

                        Icon(
                            imageVector = imageVector,
                            contentDescription = "Notifications",
                            tint = tint
                        )
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
        // NavDisplay로 화면 전환 처리
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = { entry ->
                when (entry) {
                    is ContestListScreenEntry -> NavEntry(entry) {
                        ContestListScreen(
                            onContestClick = { contest ->
                                backStack.add(ProjectListScreenEntry(contest.term))
                            },
                            padding = innerPadding
                        )
                    }

                    is ProjectListScreenEntry -> NavEntry(entry) {
                        ProjectListScreen(
                            contestTerm = entry.term,
                            onProjectClick = {project ->
                                backStack.add(ProjectDetailScreenEntry(project.id))},
                            onShowErrorSnackBar = {},
                            padding = innerPadding
                        )
                    }

                    is UserScreenEntry -> NavEntry(entry) {
                        UserScreen(padding = innerPadding)
                    }

                    is ProjectDetailScreenEntry -> NavEntry(entry) {
                        ProjectDetailScreen(
                            projectId = entry.projectId,
                            onBack = { backStack.removeLastOrNull() }
                        )
                    }

                    is NoticeScreenEntry -> NavEntry(entry) {
                        val currentUserEmail by authViewModel.currentUserEmail.collectAsState()
                        NoticeScreen(
                            userEmail = currentUserEmail,
                            isLoggedIn = entry.isLoggedIn,
                            onLogoutClick = { 
                                authViewModel.signOut()
                                backStack.clear()
                                backStack.add(LoginScreenEntry)
                            },
                            onBack = { backStack.removeLastOrNull() }
                        )
                    }

                    is RegisterScreenEntry -> NavEntry(entry) {
                        RegisterScreen(
                            onBack = { backStack.removeLastOrNull() },
                            onRegisterSuccess = { backStack.add(LoginScreenEntry) }
                        )
                    }

                    is LoginScreenEntry -> NavEntry(entry) {
                        LoginScreen(
                            onBack = { backStack.removeLastOrNull() },
                            onLoginSuccess = { backStack.clear(); backStack.add(ContestListScreenEntry) },
                            onNavigateToRegister = { backStack.add(RegisterScreenEntry) }
                        )
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OpenKnightsAppPreview() {
    KnightsTheme {
        OpenKnightsApp()
    }
}
