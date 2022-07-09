package com.example.api.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Database
import androidx.room.DatabaseView
import com.example.api.data.ApiAthlete
import com.example.api.data.ApiToken
import com.example.api.data.HeaderInterceptor
import com.example.utils.ObjectApp.SHARED_PREFS_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ModuleNetworking {

    @Provides
    fun providesApiForToken(): ApiToken = providesRetrofitForToken().create()

    @Singleton
    @Provides
    fun providesRetrofitForToken(): Retrofit = Retrofit.Builder()
        .baseUrl("https://google.com")
        .addConverterFactory(providesMoshiConverter())
        .client(providesOkHttpClientForToken())
        .build()

    @Singleton
    @Provides
    fun providesOkHttpClientForToken() = OkHttpClient.Builder()
        .addNetworkInterceptor(
            providesHttpLoggingInterceptor()
        )
        .build()

    @Provides
    fun providesApiAthlete(@ApplicationContext context: Context): ApiAthlete =
        providesRetrofitAthlete(context).create()

    @Singleton
    @Provides
    fun providesRetrofitAthlete(context: Context): Retrofit = Retrofit.Builder()
        .baseUrl("https://www.strava.com")
        .addConverterFactory(providesMoshiConverter())
        .client(providesOkHttpClientAthlete(context))
        .build()

    @Singleton
    @Provides
    fun providesOkHttpClientAthlete(context: Context) = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor(providesApiForToken(), context))
        .addNetworkInterceptor(
            providesHttpLoggingInterceptor()
        )
        .build()


    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun providesMoshiConverter() = MoshiConverterFactory.create()
}
