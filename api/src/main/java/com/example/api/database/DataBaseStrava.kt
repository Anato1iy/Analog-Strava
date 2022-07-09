package com.example.api.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.api.database.models.list_activities.ListActivitiesDB
import com.example.api.database.models.list_activities.ListActivitiesDaoDB
import com.example.api.database.models.profile.ProfileDao
import com.example.stravarun.database.models.profile.Profile

@Database(
    entities =
    [Profile::class, ListActivitiesDB::class],
    version = DataBaseStrava.DB_VERSION
)
abstract class DataBaseStrava : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun listActivitiesDao(): ListActivitiesDaoDB

    companion object {
        const val DB_NAME = "strava-database"
        const val DB_VERSION = 1
    }
}