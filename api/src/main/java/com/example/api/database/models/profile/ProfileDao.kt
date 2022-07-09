package com.example.api.database.models.profile

import androidx.room.*
import com.example.stravarun.database.models.profile.Profile

@Dao
interface ProfileDao {

    @Query("SELECT * FROM ${ProfileContract.TABLE_NAME}")
    suspend fun getProfile(): Profile

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: Profile)
}