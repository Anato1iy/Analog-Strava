package com.example.api.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Athlete(
    val id: String,
    val firstname: String,
    val lastname: String,
    val country: String,
    val sex: String,
    val weight: Int,
    val profile: String,
    @Json(name = "follower_count")
    val followerCount: Int,
    @Json(name = "friend_count")
    val friendCount: Int
)
