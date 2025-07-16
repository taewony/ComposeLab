package com.openknights.app.core.navigator.impl

import com.openknights.app.core.navigator.api.Navigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module: core/navigator/impl - 내비게이션 관련 의존성 주입을 설정합니다.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class NavigatorModule {

    @Singleton
    @Binds
    abstract fun bindNavigator(navigatorImpl: NavigatorImpl): Navigator
}