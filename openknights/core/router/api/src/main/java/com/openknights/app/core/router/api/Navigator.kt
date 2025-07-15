package com.openknights.app.core.router.api

interface Navigator {
    fun navigate(uri: String)
    fun navigateBack()
}
