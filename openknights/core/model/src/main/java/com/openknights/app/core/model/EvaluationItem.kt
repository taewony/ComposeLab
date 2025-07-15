package com.openknights.app.core.model

data class EvaluationItem(
    val id: String,
    val name: String,
    val description: String
)

enum class EvaluationCategory {
    CREATIVITY,
    PRACTICALITY
}
