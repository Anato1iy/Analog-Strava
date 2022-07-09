package com.example.auth.data


import android.content.Context
import com.example.api.data.ApiToken
import com.example.api.data.AuthConfig
import com.example.utils.ObjectApp.ACCESS_TOKEN
import com.example.utils.ObjectApp.EXPIRES_AT_TOKEN
import com.example.utils.ObjectApp.EXPIRES_IN_TOKEN
import com.example.utils.ObjectApp.FIRST_STARTING
import com.example.utils.ObjectApp.REFRESH_TOKEN
import com.example.utils.ObjectApp.SHARED_PREFS_NAME
import com.example.utils.ObjectApp.TOKEN_TYPE
import dagger.hilt.android.qualifiers.ApplicationContext
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.TokenRequest
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authorizationRequest: AuthorizationRequest,
    private val apiToken: ApiToken,
    @ApplicationContext val context: Context
) : AuthRepository {

    private val sharedPrefs =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)


    override fun getAuthRequest(): AuthorizationRequest {
        return authorizationRequest
    }

    override fun isHaveToken(): Boolean {
        val token = sharedPrefs.getString(ACCESS_TOKEN, null)
        return !token.equals(null)
    }

    override suspend fun performTokenRequest(tokenRequest: TokenRequest) {
        val authorizationCode = tokenRequest.authorizationCode
        if (authorizationCode != null) {
            val token = apiToken.getToken(
                AuthConfig.CLIENT_ID,
                AuthConfig.CLIENT_SECRET,
                authorizationCode,
                "authorization_code"
            )

            sharedPrefs.edit().putString(TOKEN_TYPE,token.tokenType).apply()
            sharedPrefs.edit().putLong(EXPIRES_AT_TOKEN, token.expiresAt).apply()
            sharedPrefs.edit().putLong(EXPIRES_IN_TOKEN, token.expiresIn).apply()
            sharedPrefs.edit().putString(REFRESH_TOKEN,token.refreshToken).apply()
            sharedPrefs.edit().putString(ACCESS_TOKEN,token.accessToken).apply()
        }
    }

    override fun isFirstStart(): Boolean {
        val isFirst = sharedPrefs.getBoolean(FIRST_STARTING,true)
        if (isFirst){
            sharedPrefs.edit().putBoolean(FIRST_STARTING,false).apply()
        }
        return isFirst
    }
}