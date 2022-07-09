package com.example.auth.data

import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.TokenRequest

interface AuthRepository {
    fun getAuthRequest(): AuthorizationRequest
    suspend fun performTokenRequest(tokenRequest: TokenRequest)
    fun isHaveToken(): Boolean
    fun isFirstStart(): Boolean
}