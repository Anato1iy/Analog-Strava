package com.example.activity.di

import com.example.activity.date.ActivityRepository
import com.example.activity.date.ActivityRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun providesActivityRepository(impl: ActivityRepositoryImpl): ActivityRepository
}