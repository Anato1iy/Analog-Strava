package com.example.api.data

import net.openid.appauth.ResponseTypeValues

object AuthConfig {
    const val AUTH_URI = "https://www.strava.com/oauth/authorize"
    const val TOKEN_URI = "https://www.strava.com/oauth/token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "activity:read_all,activity:write,profile:read_all,profile:write"
    const val PROMPT = "auto"

    const val CLIENT_ID = "68701"
    const val CLIENT_SECRET = "9816b6385519ac507c3f31371c6521f6d21f6b2e"
    const val CALLBACK_URL = "callback://developers.strava.com"

    //token
 //   var Token:AuthTok? = null
}