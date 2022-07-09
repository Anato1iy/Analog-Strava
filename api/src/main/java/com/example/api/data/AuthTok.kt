package com.example.api.data
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthTok(
    @Json(name = "token_type")
    val tokenType: String,
    @Json(name = "expires_at")
    val expiresAt: Long,
    @Json(name = "expires_in")
    val expiresIn: Long,
    @Json(name = "refresh_token")
    val refreshToken: String,
    @Json(name = "access_token")
    val accessToken: String
)


