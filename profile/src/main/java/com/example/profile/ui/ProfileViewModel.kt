package com.example.profile.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.data.Athlete
import com.example.profile.data.ProfileRepository
import com.example.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepositoryImpl: ProfileRepository
) : ViewModel() {

    private val isLogoutLiveEvent = SingleLiveEvent<Boolean>()
    val isLogout: LiveData<Boolean>
        get() = isLogoutLiveEvent
    private val athleteLiveData = MutableLiveData<Athlete>()
    val athlete: LiveData<Athlete>
        get() = athleteLiveData

    fun getProfileAthlete() {
        viewModelScope.launch {
            try {
                val athlete = profileRepositoryImpl.getProfileAthlete()
                athleteLiveData.postValue(athlete)
            } catch (t: Throwable) {
                Log.d("stravaError","ProfileViewModel ${t.message.toString()}")
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            try {
                profileRepositoryImpl.logout()
                isLogoutLiveEvent.postValue(true)
            }
            catch (t: Throwable){
                isLogoutLiveEvent.postValue(false)
            }
        }
    }

    fun updateWeight(weight: Int){
        viewModelScope.launch {
            try {
                profileRepositoryImpl.updateWeight(weight)
            } catch (t: Throwable) {
                Log.d("asd",t.message.toString())
            }
        }
    }

    fun isPushNotif():Boolean{
       return profileRepositoryImpl.isPushNotif()
    }
}
