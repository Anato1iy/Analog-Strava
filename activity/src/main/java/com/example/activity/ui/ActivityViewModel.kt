package com.example.activity.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activity.date.ActivityRepository
import com.example.api.data.ListActivitiesWithProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val activityRepositoryImpl: ActivityRepository
) : ViewModel() {

    private val listActivityLiveData = MutableLiveData<List<ListActivitiesWithProfile>>()
    val activityList: LiveData<List<ListActivitiesWithProfile>>
        get() = listActivityLiveData
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData
    private val isErrorActivityLiveData = MutableLiveData<Boolean>()
    val isErrorActivity: LiveData<Boolean>
        get() = isErrorActivityLiveData
    private val isCacheLiveData = MutableLiveData<Boolean>()
    val isCache: LiveData<Boolean>
        get() = isCacheLiveData

    fun getListActivity() {
        viewModelScope.launch {
            try {
                isErrorActivityLiveData.postValue(false)
                isLoadingLiveData.postValue(true)
                val listActivity = activityRepositoryImpl.listActivity(
                    callbackIsCache = {
                        isCacheLiveData.postValue(it)
                    }
                )
                listActivityLiveData.postValue(listActivity)
                isLoadingLiveData.postValue(false)

            } catch (t: Throwable) {
                isLoadingLiveData.postValue(false)
                isErrorActivityLiveData.postValue(true)
            }
        }
    }

}