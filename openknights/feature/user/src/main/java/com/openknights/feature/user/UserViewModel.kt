package com.openknights.feature.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openknights.core.data.user.UserRepository
import com.openknights.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.getAllUsers().collectLatest {
                _users.value = it
            }
        }
    }
}
