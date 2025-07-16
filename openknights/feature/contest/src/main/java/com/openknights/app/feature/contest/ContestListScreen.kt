package com.openknights.app.feature.contest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.openknights.app.core.model.Contest
import com.openknights.app.core.testing.FakeOpenKnightsData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

/**
 * Module: feature/contest - 대회 목록 화면을 정의합니다.
 */
@Composable
internal fun ContestListScreen(
    padding: PaddingValues,
    onContestClick: (Contest) -> Unit,
    viewModel: ContestListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        ContestListUiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        is ContestListUiState.Success -> {
            ContestListContent(
                contests = (uiState as ContestListUiState.Success).contests,
                modifier = Modifier.padding(padding),
                onContestClick = onContestClick
            )
        }
    }
}

@Composable
private fun ContestListContent(
    contests: ImmutableList<Contest>,
    modifier: Modifier = Modifier,
    onContestClick: (Contest) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "우송대 오픈소스 경진대회",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        items(contests) { contest ->
            ContestCard(contest, onContestClick)
        }
    }
}

@Composable
private fun ContestCard(contest: Contest, onContestClick: (Contest) -> Unit) {
    // TODO: ContestCard UI 구현
    // 현재는 간단한 Text로 대체
    Column(modifier = Modifier.clickable { onContestClick(contest) }) {
        Text(text = contest.name, style = MaterialTheme.typography.titleLarge)
        Text(text = contest.term, style = MaterialTheme.typography.bodyMedium)
        Text(text = contest.topic, style = MaterialTheme.typography.bodySmall)
    }
}

@Preview(showBackground = true)
@Composable
private fun ContestListScreenPreview() {
    ContestListContent(
        contests = FakeOpenKnightsData.fakeContests.toImmutableList(),
        onContestClick = {}
    )
}