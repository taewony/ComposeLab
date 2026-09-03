package com.example.app_13_cupcake.navigation

import androidx.navigation3.runtime.NavKey as LibraryNavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class CupcakeNavKey : LibraryNavKey {
    @Serializable data object Start : CupcakeNavKey()
    @Serializable data object Flavor : CupcakeNavKey()
    @Serializable data object Pickup : CupcakeNavKey()
    @Serializable data object Summary : CupcakeNavKey()
}