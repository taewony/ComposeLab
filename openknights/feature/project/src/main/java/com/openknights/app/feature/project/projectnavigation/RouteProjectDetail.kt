package com.openknights.app.feature.project.projectnavigation

import kotlinx.serialization.Serializable

@Serializable
data class RouteProjectDetail(val projectId: String) {
    fun toRoute(): String = "project_detail_route/$projectId"
}
