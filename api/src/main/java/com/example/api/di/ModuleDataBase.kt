package com.example.api.di

import android.content.Context
import androidx.room.Room
import com.example.api.database.DataBaseStrava
import com.example.api.database.models.list_activities.ListActivitiesDaoDB
import com.example.api.database.models.profile.ProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleDataBase {
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): DataBaseStrava =
        Room.databaseBuilder(context, DataBaseStrava::class.java, DataBaseStrava.DB_NAME)
            .build()

    @Provides
    fun providesProfileDao(dataBaseStrava: DataBaseStrava): ProfileDao =
        dataBaseStrava.profileDao()

    @Provides
    fun providesListActivitiesDao(dataBaseStrava: DataBaseStrava): ListActivitiesDaoDB =
        dataBaseStrava.listActivitiesDao()
}