package com.example.auth.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AuthorizationService


@Module
@InstallIn(ViewModelComponent::class)
class ModuleAuthService {


    @Provides
    fun authService(application: Application): AuthorizationService {
        return AuthorizationService(application)
    }
}