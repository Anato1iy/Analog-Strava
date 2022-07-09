package com.example.profile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.data.Contact
import com.example.profile.data.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val profileRepositoryImpl: ProfileRepository
) : ViewModel() {

    private val contactsListLiveData = MutableLiveData<List<Contact>>()
    val contactsList: LiveData<List<Contact>>
        get() = contactsListLiveData
    private val idAthleteLiveData = MutableLiveData<String>()
    val idAthlete: LiveData<String>
        get() = idAthleteLiveData
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun loadList() {
        viewModelScope.launch {
            try {
                isLoadingLiveData.postValue(true)
                val shareList = profileRepositoryImpl.getContact()
                contactsListLiveData.postValue(shareList)
                isLoadingLiveData.postValue(false)
            } catch (t: Throwable) {
                contactsListLiveData.postValue(emptyList())
                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun getIdAthlete() {
        viewModelScope.launch {
            try {
                val idAthlete = profileRepositoryImpl.getIdAthlete()
                idAthleteLiveData.postValue(idAthlete)
            } catch (t: Throwable) {

            }
        }
    }
}