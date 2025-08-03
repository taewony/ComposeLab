package com.openknights.app.di

import com.openknights.app.core.navigator.api.Navigator
import com.openknights.app.core.navigator.impl.NavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigatorModule {

    @Provides
    @Singleton
    fun provideNavigator(): Navigator {
        return NavigatorImpl()
    }
}