package com.openknights.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _isLoggedIn = MutableStateFlow(auth.currentUser != null)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _currentUserEmail = MutableStateFlow<String?>(auth.currentUser?.email)
    val currentUserEmail: StateFlow<String?> = _currentUserEmail

    private val authStateListener: FirebaseAuth.AuthStateListener

    init {
        authStateListener = FirebaseAuth.AuthStateListener {
            val loggedIn = it.currentUser != null
            _isLoggedIn.value = loggedIn
            _currentUserEmail.value = it.currentUser?.email
            Log.d("AuthViewModel", "Auth state changed: isLoggedIn = $loggedIn, currentUser = ${it.currentUser?.email}")
        }
        auth.addAuthStateListener(authStateListener)
        Log.d("AuthViewModel", "Initial auth state: isLoggedIn = ${_isLoggedIn.value}, currentUser = ${auth.currentUser?.email}")
    }

    override fun onCleared() {
        super.onCleared()
        auth.removeAuthStateListener(authStateListener)
        Log.d("AuthViewModel", "AuthViewModel cleared, listener removed.")
    }

    fun registerUser(email: String, password: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null, success = false)
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            _uiState.value = _uiState.value.copy(isLoading = false, success = true)
                        } else {
                            _uiState.value = _uiState.value.copy(isLoading = false, error = it.exception?.message)
                        }
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun loginUser(email: String, password: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null, success = false)
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            _uiState.value = _uiState.value.copy(isLoading = false, success = true)
                        } else {
                            _uiState.value = _uiState.value.copy(isLoading = false, error = it.exception?.message)
                        }
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun resetState() {
        _uiState.value = AuthUiState()
    }

    fun signOut() {
        auth.signOut()
        Log.d("AuthViewModel", "User signed out.")
    }
}

data class AuthUiState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)
