package com.example.openknights

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.openknights.core.designsystem.theme.KnightsTheme
import com.openknights.core.testing.FakeData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KnightsTheme {
                DroidKnightsApp()
            }
        }
    }
}

sealed class Screen(val route: String, val titleResId: Int, val icon: @Composable () -> Unit) {
    object Home : Screen("home", R.string.home_screen_title, { Icon(Icons.Filled.Home, contentDescription = null) })
    object Team : Screen("team", R.string.team_screen_title, { Icon(Icons.Filled.Group, contentDescription = null) })
    object Submission : Screen("submission", R.string.submission_screen_title, { Icon(Icons.Filled.Send, contentDescription = null) })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DroidKnightsApp() {
    val navController = rememberNavController()
    val screens = listOf(Screen.Home, Screen.Team, Screen.Submission)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomAppBar {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    screens.forEach { screen ->
                        NavigationBarItem(
                            icon = screen.icon,
                            label = { Text(stringResource(id = screen.titleResId)) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Home.route, Modifier.padding(innerPadding)) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Team.route) { TeamScreen() }
            composable(Screen.Submission.route) { SubmissionScreen() }
        }
    }
}

@Composable
fun HomeScreen() {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item { Text(text = "Contests", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 8.dp)) }
        items(FakeData.fakeContests) { contest ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = contest.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = contest.description, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun TeamScreen() {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item { Text(text = "Teams", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 8.dp)) }
        items(FakeData.fakeTeams) { team ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = team.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Members: " + team.members.joinToString { member -> member.name }, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun SubmissionScreen() {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item { Text(text = "Submissions", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 8.dp)) }
        items(FakeData.fakeProjects) { project ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = project.title, style = MaterialTheme.typography.titleMedium)
                    Text(text = project.description, style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Likes: " + project.likes, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DroidKnightsAppPreview() {
    KnightsTheme {
        DroidKnightsApp()
    }
}