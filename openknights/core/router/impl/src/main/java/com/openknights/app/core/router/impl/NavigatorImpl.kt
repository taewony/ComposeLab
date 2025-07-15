package com.openknights.app.core.router.impl

import com.openknights.app.core.router.api.Navigator
import javax.inject.Inject

class NavigatorImpl @Inject constructor() : Navigator {
    override fun navigate(uri: String) {
        // TODO: Implement actual navigation logic
        println("Navigating to: $uri")
    }

    override fun navigateBack() {
        // TODO: Implement actual back navigation logic
        println("Navigating back")
    }
}
