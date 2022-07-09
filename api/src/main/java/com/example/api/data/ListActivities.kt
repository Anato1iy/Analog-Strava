package com.example.api.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class ListActivities(
    val name: String,
    val distance: String,
    @Json(name = "elapsed_time")
    val time: String,
    @Json(name = "start_date")
    val startDate: String,
    val type: String,
    @Json(name = "total_elevation_gain")
    val elevationGain: String
)