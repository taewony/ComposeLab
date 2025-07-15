package com.openknights.app.feature.user

import androidx.lifecycle.ViewModel
import com.openknights.app.core.testing.FakeUsers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor() : ViewModel() {
    val users = FakeUsers.users
}