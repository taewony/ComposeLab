package com.openknights.feature.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.openknights.core.data.user.UserRepositoryImpl
// import com.google.firebase.Firebase
// import com.google.firebase.firestore.firestore

import android.util.Log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(padding: PaddingValues) {
    Log.d("UserScreen", "UserScreen is composed") // Debug print
    // Firebase.firestore를 사용하여 UserRepository 인스턴스를 생성합니다.
    val userRepository = UserRepositoryImpl(/* Firebase.firestore */)
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(userRepository))
    val users by userViewModel.users.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("사용자 목록") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(users) {
                user ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "이메일: ${user.email}", style = MaterialTheme.typography.bodyLarge)
                        Text(text = "UID: ${user.uid}", style = MaterialTheme.typography.bodyMedium)
                        user.name?.let { Text(text = "이름: $it", style = MaterialTheme.typography.bodyMedium) }
                    }
                }
            }
        }
    }
}
