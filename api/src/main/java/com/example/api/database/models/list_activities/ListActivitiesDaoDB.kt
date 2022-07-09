package com.example.api.database.models.list_activities

import androidx.room.*

@Dao
interface ListActivitiesDaoDB {

    @Query("SELECT * FROM ${ListActivitiesContractDB.TABLE_NAME} ORDER BY ${
        ListActivitiesContractDB.Columns.START_DATE} DESC")
    suspend fun getListActivities(): List<ListActivitiesDB>

    @Query("SELECT * FROM ${ListActivitiesContractDB.TABLE_NAME} WHERE ${ListActivitiesContractDB.Columns.SYNCHRONIZED_LIST} = 0 ")
    suspend fun getListActivitiesNoSynch(): List<ListActivitiesDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListActivities(listActivities: List<ListActivitiesDB>)

    @Update
    suspend fun updateListActivities(activities: ListActivitiesDB)
}