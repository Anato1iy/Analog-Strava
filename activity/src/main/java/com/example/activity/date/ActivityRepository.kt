package com.example.activity.date

import com.example.api.data.ListActivitiesWithProfile

interface ActivityRepository {
    suspend fun listActivity(callbackIsCache: (Boolean) -> Unit): List<ListActivitiesWithProfile>
    suspend fun addActivities(activities: Activities)
}