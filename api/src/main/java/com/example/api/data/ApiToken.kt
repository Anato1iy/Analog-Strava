package com.example.api.data

import com.example.utils.ObjectApp
import com.example.utils.ObjectApp.STRAVA
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiToken {
    @POST("${STRAVA}/api/v3/oauth/token")
    @FormUrlEncoded
    suspend fun getToken(
        @Field("client_id")
        clientId: String,
        @Field("client_secret")
        clientSecret: String,
        @Field("code")
        code: String,
        @Field("grant_type")
        grantType: String
    ): AuthTok

    @POST("${STRAVA}/api/v3/oauth/token")
    @FormUrlEncoded
    fun refreshToken(
        @Field("client_id")
        clientId: String,
        @Field("client_secret")
        clientSecret: String,
        @Field("grant_type")
        grantType: String,
        @Field("refresh_token")
        code: String
    ): Call<AuthTok>

    @POST("${STRAVA}/oauth/deauthorize")
    @FormUrlEncoded
    suspend fun logout(
        @Field("access_token")
        accessToken: String
    )
}