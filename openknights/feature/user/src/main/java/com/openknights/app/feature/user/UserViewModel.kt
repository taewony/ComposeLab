package com.openknights.app.feature.user

import androidx.lifecycle.ViewModel
import com.openknights.app.core.testing.FakeUsers


class UserViewModel constructor() : ViewModel() {
    val users = FakeUsers.users
}