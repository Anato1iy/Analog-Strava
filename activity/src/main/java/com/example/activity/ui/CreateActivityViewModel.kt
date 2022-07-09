package com.example.activity.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activity.date.Activities
import com.example.activity.date.ActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateActivityViewModel @Inject constructor(
    private val activityRepositoryImpl: ActivityRepository
) : ViewModel() {

    private val dateLiveData = MutableLiveData<Calendar>()
    val date: LiveData<Calendar>
        get() = dateLiveData

    fun saveDate(date: Calendar) {
        dateLiveData.postValue(date)
    }


    fun addActivities(activities: Activities) {
        viewModelScope.launch {
            try {
                activityRepositoryImpl.addActivities(activities)
            } catch (t: Throwable) {
                Log.d("StravaError","Error add Activities ${t.message.toString()}")
            }
        }
    }
}