package com.openknights.app.core.router.impl

import com.openknights.app.core.router.api.Navigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigatorModule {

    @Singleton
    @Binds
    abstract fun bindNavigator(navigatorImpl: NavigatorImpl): Navigator
}
