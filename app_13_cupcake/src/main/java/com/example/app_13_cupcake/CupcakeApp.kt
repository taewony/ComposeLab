package com.example.app_13_cupcake

import androidx.compose.runtime.*
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.app_13_cupcake.navigation.CupcakeNavKey
import com.example.app_13_cupcake.viewmodel.OrderViewModel
import com.example.app_13_cupcake.screen.FlavorScreen
import com.example.app_13_cupcake.screen.PickupScreen
import com.example.app_13_cupcake.screen.StartScreen
import com.example.app_13_cupcake.screen.SummaryScreen

@Composable
fun CupcakeApp(viewModel: OrderViewModel) {
    // ✅ 1. Navigation 3 백스택 생성 (주문 시작 화면: Start)
    val backStack = rememberNavBackStack(CupcakeNavKey.Start)

    // ✅ 2. NavDisplay + entry 블록 (공식 API)
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeAt(backStack.lastIndex) },  // 뒤로 가기 동작 연결
        entryProvider = entryProvider {
            entry<CupcakeNavKey.Start> {
                StartScreen(
                    onNext = { backStack.add(CupcakeNavKey.Flavor) }
                )
            }
            entry<CupcakeNavKey.Flavor> {
                FlavorScreen(
                    viewModel = viewModel,
                    onNext = { backStack.add(CupcakeNavKey.Pickup) },
                    onBack = { backStack.removeAt(backStack.lastIndex) }
                )
            }
            entry<CupcakeNavKey.Pickup> {
                PickupScreen(
                    viewModel = viewModel,
                    onNext = { backStack.add(CupcakeNavKey.Summary) },
                    onBack = { backStack.removeAt(backStack.lastIndex) }
                )
            }
            entry<CupcakeNavKey.Summary> {
                SummaryScreen(
                    viewModel = viewModel,
                    onRestart = {
                        viewModel.resetOrder()
                        backStack.clear()              // 전체 초기화
                        backStack.add(CupcakeNavKey.Start)  // 시작 화면 추가
                    }
                )
            }
        }
    )
}