package com.openknights.app.core.model

/**
 * 특정 대회에서 특정 User가 어떤 역할을 맡는지 정의하는 모델
 */
data class ContestStaff(
    val userId: String,      // User.id 참조
    val contestTerm: String, // Contest.term 참조
    val role: ContestRole,
)