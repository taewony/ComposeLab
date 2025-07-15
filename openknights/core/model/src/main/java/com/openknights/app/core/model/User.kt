package com.openknights.app.core.model

data class User(
    val id: String,
    val name: String,
    val introduction: String,
    val imageUrl: String,
    val roles: List<String> = emptyList() // 역할 추가
)