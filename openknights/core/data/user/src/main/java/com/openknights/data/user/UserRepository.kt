package com.openknights.core.data.user

// import com.google.firebase.firestore.FirebaseFirestore
import com.openknights.core.model.User
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay // Added for fake data simulation

interface UserRepository {
    fun getAllUsers(): Flow<List<User>>
    suspend fun addUserProfile(user: User)
}

class UserRepositoryImpl(/* private val firestore: FirebaseFirestore */) : UserRepository {

    override fun getAllUsers(): Flow<List<User>> = flow {
        // val snapshot = firestore.collection("users").get().await()
        // val users = snapshot.documents.mapNotNull { it.toObject(User::class.java) }
        // Log.d("UserRepositoryImpl", "Fetched users: $users") // Debug print
        // emit(users)

        // 가짜 데이터 생성
        val fakeUsers = listOf(
            User(uid = "fake_uid_1", email = "test1@example.com", name = "Test User 1"),
            User(uid = "fake_uid_2", email = "test2@example.com", name = "Test User 2")
        )
        delay(500) // 데이터 로딩 지연 시뮬레이션
        Log.d("UserRepositoryImpl", "Fetched fake users: $fakeUsers")
        emit(fakeUsers)
    }

    override suspend fun addUserProfile(user: User) {
        // firestore.collection("users").document(user.uid).set(user).await()
        // 가짜 데이터에서는 사용자 프로필 추가 로직을 수행하지 않습니다.
        Log.d("UserRepositoryImpl", "addUserProfile called with fake data: $user")
    }
}
