package com.example.api.database.models.list_activities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.api.database.models.list_activities.ListActivitiesContractDB

@Entity(tableName = ListActivitiesContractDB.TABLE_NAME)
data class ListActivitiesDB(
    @ColumnInfo(name = ListActivitiesContractDB.Columns.NAME_ACTIVITIES)
    val name: String,
    @ColumnInfo(name = ListActivitiesContractDB.Columns.DISTANCE)
    val distance: String,
    @ColumnInfo(name = ListActivitiesContractDB.Columns.TIME_ACTIVITIES)
    val time: String,
    @PrimaryKey
    @ColumnInfo(name = ListActivitiesContractDB.Columns.START_DATE)
    val startDate: String,
    @ColumnInfo(name = ListActivitiesContractDB.Columns.TYPE_ACTIVITIES)
    val type: String,
    @ColumnInfo(name = ListActivitiesContractDB.Columns.ELEVATION_GAIN)
    val elevationGain: String,
    @ColumnInfo(name = ListActivitiesContractDB.Columns.DESCRIPTIONS)
    val description: String,
    @ColumnInfo(name = ListActivitiesContractDB.Columns.SYNCHRONIZED_LIST)
    val synchronizedList: Boolean
)
