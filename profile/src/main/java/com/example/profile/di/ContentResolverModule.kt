package com.example.profile.di

import android.content.ContentResolver
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ContentResolverModule {
    @Provides
    @Singleton
    fun providesContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }
}