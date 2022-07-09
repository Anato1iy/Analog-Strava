package com.example.stravarun.database.models.profile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.api.database.models.profile.ProfileContract

@Entity(tableName = ProfileContract.TABLE_NAME)
data class Profile(
    @PrimaryKey
    @ColumnInfo(name = ProfileContract.Columns.ID)
    val id: String,
    @ColumnInfo(name = ProfileContract.Columns.FIRSTNAME)
    val firstName: String,
    @ColumnInfo(name = ProfileContract.Columns.LASTNAME)
    val lastName: String,
    @ColumnInfo(name = ProfileContract.Columns.COUNTRY)
    val country: String,
    @ColumnInfo(name = ProfileContract.Columns.SEX)
    val sex: String,
    @ColumnInfo(name = ProfileContract.Columns.WEIGHT)
    val weight: Int,
    @ColumnInfo(name = ProfileContract.Columns.PROFILE)
    val profile: String,
    @ColumnInfo(name = ProfileContract.Columns.FOLLOWERCOUNT)
    val followerCount: Int,
    @ColumnInfo(name = ProfileContract.Columns.FRIENDCOUNT)
    val friendCount: Int
)