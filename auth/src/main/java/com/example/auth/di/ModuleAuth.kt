package com.example.auth.di

import android.net.Uri
import com.example.api.data.AuthConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationServiceConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleAuth {

    @Provides
    @Singleton
    fun providesAuthorizationRequest(): AuthorizationRequest {
        return AuthorizationRequest.Builder(
            providesAuthorizationServiceConfiguration(
                providesAuthorizationEndpoint(),
                providesTokenEndpoint()
            ),
            AuthConfig.CLIENT_ID,
            AuthConfig.RESPONSE_TYPE,
            providesRedirectUri()
        ).setScope(AuthConfig.SCOPE)
            .setPrompt(AuthConfig.PROMPT)
            .build()
    }

    @Provides
    @Singleton
    fun providesAuthorizationServiceConfiguration(
       authorizationEndpoint: Uri,
       tokenEndpoint: Uri
    ): AuthorizationServiceConfiguration {
        return AuthorizationServiceConfiguration(
            authorizationEndpoint, tokenEndpoint
        )
    }


    @Provides
    @Singleton
    fun providesAuthorizationEndpoint(): Uri {
        return Uri.parse(AuthConfig.AUTH_URI)
    }


    @Provides
    @Singleton
    fun providesTokenEndpoint(): Uri {
        return Uri.parse(AuthConfig.AUTH_URI)
    }


    @Provides
    @Singleton
    fun providesRedirectUri(): Uri {
        return Uri.parse(AuthConfig.CALLBACK_URL)
    }
}