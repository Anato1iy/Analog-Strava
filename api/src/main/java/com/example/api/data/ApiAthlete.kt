package com.example.api.data

import com.example.utils.ObjectApp.STRAVA
import retrofit2.http.*

interface ApiAthlete {
    @GET("${STRAVA}/api/v3/athlete")
    suspend fun getProfileAthlete(): Athlete

    @PUT("${STRAVA}/api/v3/athlete")
    @FormUrlEncoded
    suspend fun updateWeight(
        @Field("weight")
        weight: Float
    )

    @POST("${STRAVA}/api/v3/activities")
    @FormUrlEncoded
    suspend fun createActivity(
        @Field("name")
        name: String,
        @Field("type")
        type: String,
        @Field("start_date_local")
        date: String,
        @Field("elapsed_time")
        time: Int,
        @Field("description")
        description: String,
        @Field("distance")
        distance: Float,
        @Field("trainer")
        trainer: Int,
        @Field("commute")
        commute: Int
    )

    @GET("${STRAVA}/api/v3/athlete/activities")
    suspend fun getListActivities(): List<ListActivities>
}