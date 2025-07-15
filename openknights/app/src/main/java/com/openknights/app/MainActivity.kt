package com.openknights.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.openknights.app.core.designsystem.theme.KnightsTheme
import com.openknights.app.feature.contest.navigation.CONTEST_LIST_ROUTE
import com.openknights.app.feature.contest.navigation.contestListScreen
import com.openknights.app.feature.project.projectnavigation.RouteProjectList
import com.openknights.app.feature.project.projectnavigation.projectNavGraph
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

// Screen sealed class는 ContestListScreen을 초기 화면으로 설정하므로 제거하거나 수정
// 현재는 ContestListScreen만 사용하므로 BottomNavigation 제거

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenKnightsApp() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        // CORRECTED NavHost
        NavHost(navController, startDestination = CONTEST_LIST_ROUTE, Modifier.padding(innerPadding)) {
            contestListScreen(
                onContestClick = { contest ->
                    // CORRECT: Pass the type-safe route object directly.
                    navController.navigate(RouteProjectList(contest.term))
                }
            )
            projectNavGraph(
                onShowErrorSnackBar = { throwable -> /* TODO: Show snackbar */ }
            )
        }
    }
}

// 기존 HomeScreen, TeamScreen, SubmissionScreen 제거

@Preview(showBackground = true)
@Composable
fun OpenKnightsAppPreview() {
    KnightsTheme {
        OpenKnightsApp()
    }
}