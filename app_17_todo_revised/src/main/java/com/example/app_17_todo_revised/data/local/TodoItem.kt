package com.example.app_17_todo_revised.data.local

import java.util.UUID

data class TodoItem (
    val id: UUID = UUID.randomUUID(),
    val task: String,
    val createdAt: Long = System.currentTimeMillis() // 생성 시각
)