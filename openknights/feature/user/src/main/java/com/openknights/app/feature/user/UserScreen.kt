package com.openknights.app.feature.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.openknights.app.core.model.User
import com.openknights.app.core.ui.TextChip
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import com.openknights.app.core.designsystem.theme.KnightsColor
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow

/**
 * Module: feature/user - 사용자 목록 화면을 정의합니다.
 * Hilt가 알아서 UserViewModel 인스턴스를 주입해 줍니다.
 * 별도 초기화 코드 없이 ViewModel을 얻을 수 있어, 테스트와 유연성 확보됩니다.
 * Screen: User List
 */
@Composable
fun UserScreen(viewModel: UserViewModel = hiltViewModel()) {
    val users = viewModel.users

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(users) {
            user ->
            UserCard(user)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserCard(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = user.name, style = MaterialTheme.typography.headlineSmall)
                if (user.roles.isNotEmpty()) {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        user.roles.forEach { role ->
                            TextChip(
                                text = role,
                                containerColor = KnightsColor.Blue01, // 남색으로 변경
                                textColor = KnightsColor.White
                            )
                        }
                    }
                }
            }
            Text(text = user.introduction, style = MaterialTheme.typography.bodyMedium)
        }
    }
}