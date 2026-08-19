package com.example.app_13_cupcake.navigation

sealed interface CupcakeScreen {
    data object Start : CupcakeScreen
    data object Flavor : CupcakeScreen
    data object Pickup : CupcakeScreen
    data object Summary : CupcakeScreen
}
