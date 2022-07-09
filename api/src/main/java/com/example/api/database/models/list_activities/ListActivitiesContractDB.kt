package com.example.api.database.models.list_activities

object ListActivitiesContractDB {
    const val TABLE_NAME = "list_activities"

    object Columns {
        const val NAME_ACTIVITIES = "name"
        const val DISTANCE = "distance"
        const val TIME_ACTIVITIES = "time"
        const val START_DATE = "start_date"
        const val TYPE_ACTIVITIES = "type"
        const val ELEVATION_GAIN = "elevationGain"
        const val DESCRIPTIONS = "description"
        const val SYNCHRONIZED_LIST = "synchronizedList"
    }
}