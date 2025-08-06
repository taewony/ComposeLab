package com.openknights.core.model

data class User(
    val uid: String = "",
    val email: String = "",
    val name: String? = null,
    val profileImageUrl: String? = null,
    val roles: List<String> = emptyList(),
    val introduction: String? = null
)
