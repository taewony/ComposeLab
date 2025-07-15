package com.openknights.app.feature.project.projectnavigation

import kotlinx.serialization.Serializable

@Serializable
data class RouteProjectList(val contestTerm: String) {
    fun toRoute(): String = "project_list_route/$contestTerm"
}
