package com.example.profile.data

import com.example.api.data.Athlete
import com.example.api.data.Contact

interface ProfileRepository {
    suspend fun getProfileAthlete(): Athlete
    suspend fun logout()
    suspend fun updateWeight(weight: Int)
    suspend fun getContact(): List<Contact>
    suspend fun getIdAthlete(): String
    fun isPushNotif(): Boolean
}