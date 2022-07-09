package com.example.api.data

import android.content.Context
import android.util.Log
import com.example.utils.ObjectApp
import com.example.utils.ObjectApp.ACCESS_TOKEN
import com.example.utils.ObjectApp.EXPIRES_AT_TOKEN
import com.example.utils.ObjectApp.EXPIRES_IN_TOKEN
import com.example.utils.ObjectApp.REFRESH_TOKEN
import com.example.utils.ObjectApp.SHARED_PREFS_NAME
import com.example.utils.ObjectApp.TOKEN_TYPE

import okhttp3.Interceptor
import okhttp3.Response


class HeaderInterceptor(
    private val apiToken: ApiToken,
    val context: Context
) : Interceptor {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME,Context.MODE_PRIVATE)

    override fun intercept(chain: Interceptor.Chain): Response {
        tokenVerify()
        val typeToken = sharedPreferences.getString(TOKEN_TYPE, "null")
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, "null")
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("Authorization", "$typeToken $accessToken")
            .build()
        return chain.proceed(request)
    }

    private fun tokenVerify() {
        val tokenExpiresAt = sharedPreferences.getLong(EXPIRES_AT_TOKEN, 0)
        if (tokenExpiresAt != 0L) {
            val time = System.currentTimeMillis() / 1000
            if (tokenExpiresAt <= time) {
                Log.d("stravaError", "RefreshToken $tokenExpiresAt <= $time")
                try {
                    val newToken = getNewToken()
                    if (newToken != null) {
                        with(sharedPreferences) {
                            edit().putString(TOKEN_TYPE, newToken.tokenType).apply()
                            edit().putLong(EXPIRES_AT_TOKEN, newToken.expiresAt).apply()
                            edit().putLong(EXPIRES_IN_TOKEN, newToken.expiresIn).apply()
                            edit().putString(REFRESH_TOKEN, newToken.refreshToken).apply()
                            edit().putString(ACCESS_TOKEN, newToken.accessToken).apply()
                        }
                    }
                } catch (t: Throwable) {
                    Log.d("stravaError", "RefreshToken error")
                }
            }
        }
    }

    private fun getNewToken(): AuthTok? {
        val refreshToken = sharedPreferences.getString(REFRESH_TOKEN, null)
        return refreshToken?.let {
            apiToken.refreshToken(
                AuthConfig.CLIENT_ID,
                AuthConfig.CLIENT_SECRET,
                "refresh_token",
                it
            ).execute().body()
        }
    }
}