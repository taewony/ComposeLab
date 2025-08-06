package com.openknights.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// import com.google.firebase.auth.FirebaseAuth
// import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import kotlinx.coroutines.delay // For simulating network delay

import com.openknights.core.model.User
import com.openknights.core.data.user.UserRepository
import com.openknights.core.data.user.UserRepositoryImpl

class AuthViewModel() : ViewModel() {

    private val userRepository: UserRepository = UserRepositoryImpl()

    // private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _isLoggedIn = MutableStateFlow(false) // Default to not logged in for fake data
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _currentUserEmail = MutableStateFlow<String?>(null)
    val currentUserEmail: StateFlow<String?> = _currentUserEmail

    // private val authStateListener: FirebaseAuth.AuthStateListener

    init {
        // authStateListener = FirebaseAuth.AuthStateListener {
        //     val loggedIn = it.currentUser != null
        //     _isLoggedIn.value = loggedIn
        //     _currentUserEmail.value = it.currentUser?.email
        //     Log.d("AuthViewModel", "Auth state changed: isLoggedIn = $loggedIn, currentUser = ${it.currentUser?.email}")
        // }
        // auth.addAuthStateListener(authStateListener)
        // Log.d("AuthViewModel", "Initial auth state: isLoggedIn = ${_isLoggedIn.value}, currentUser = ${auth.currentUser?.email}")
        Log.d("AuthViewModel", "AuthViewModel initialized with fake data.")
    }

    override fun onCleared() {
        super.onCleared()
        // auth.removeAuthStateListener(authStateListener)
        Log.d("AuthViewModel", "AuthViewModel cleared, no Firebase listener.")
    }

    fun registerUser(email: String, password: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null, success = false)
        viewModelScope.launch {
            delay(1000) // Simulate network delay
            if (email.contains("@") && password.length >= 6) {
                _uiState.value = _uiState.value.copy(isLoading = false, success = true)
                // Simulate user creation and adding profile
                val fakeUid = "fake_uid_" + System.currentTimeMillis()
                val user = User(uid = fakeUid, email = email, name = "Fake User")
                userRepository.addUserProfile(user)
                Log.d("AuthViewModel", "Fake user registered: $email")
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "Invalid email or password (fake data).")
            }
        }
    }

    fun loginUser(email: String, password: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null, success = false)
        viewModelScope.launch {
            delay(1000) // Simulate network delay
            if (email == "test@example.com" && password == "password") {
                _uiState.value = _uiState.value.copy(isLoading = false, success = true)
                _isLoggedIn.value = true
                _currentUserEmail.value = email
                Log.d("AuthViewModel", "Fake user logged in: $email")
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "Invalid credentials (fake data).")
            }
        }
    }

    fun resetState() {
        _uiState.value = AuthUiState()
    }

    fun signOut() {
        _isLoggedIn.value = false
        _currentUserEmail.value = null
        Log.d("AuthViewModel", "Fake user signed out.")
    }
}

data class AuthUiState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)
