
package com.openknights.app.core.model

// Contributor 모델
data class Contributor(
    val userId: String,      // User.id 참조
    val contestTerm: String, // 기여한 시점의 Contest.term 참조
)
