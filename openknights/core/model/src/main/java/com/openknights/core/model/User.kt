package com.openknights.core.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val roles: List<Role>
)
