package com.example.auth.ui

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.R
import com.example.auth.data.AuthRepository
import com.example.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepository,
    private val authService: AuthorizationService
):ViewModel() {

    private val openAuthPageLiveEvent = SingleLiveEvent<Intent>()
    private val toastLiveEvent = SingleLiveEvent<Int>()
    private val loadingMutableLiveData = MutableLiveData(false)
    private val authSuccessLiveEvent = SingleLiveEvent<Unit>()
    private val isHaveTokenMutableLiveData = MutableLiveData<Boolean>()

    val openAuthPageLiveData: LiveData<Intent>
        get() = openAuthPageLiveEvent

    val loadingLiveData: LiveData<Boolean>
        get() = loadingMutableLiveData

    val toastLiveData: LiveData<Int>
        get() = toastLiveEvent

    val authSuccessLiveData: LiveData<Unit>
        get() = authSuccessLiveEvent

    val isHaveToken: LiveData<Boolean>
        get() = isHaveTokenMutableLiveData

    fun isHaveToken() {
        try {
            val isHave = authRepositoryImpl.isHaveToken()
            isHaveTokenMutableLiveData.postValue(isHave)
        } catch (t: Throwable) {
            Log.d("stravaError", t.message.toString())
        }
    }

    fun onAuthCodeReceived(tokenRequest: TokenRequest) {
        loadingMutableLiveData.postValue(true)
        viewModelScope.launch {
            try {
                authRepositoryImpl.performTokenRequest(tokenRequest)
                loadingMutableLiveData.postValue(false)
                authSuccessLiveEvent.postValue(Unit)
            }
            catch (t:Throwable){
                loadingMutableLiveData.postValue(false)
                toastLiveEvent.postValue(R.string.auth_canceled)
                Log.d("stravaError", t.message.toString())
            }
        }
    }
    fun onAuthCodeFailed(exception: AuthorizationException) {
        toastLiveEvent.postValue(R.string.auth_canceled)
        Log.d("stravaError", exception.message.toString())
    }

    fun openLoginPage(application: Context) {
        val customTabsIntent = CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(application, R.color.black))
            .build()

        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            authRepositoryImpl.getAuthRequest(),
            customTabsIntent
        )

        openAuthPageLiveEvent.postValue(openAuthPageIntent)
    }

    fun isFirstStart():Boolean{
        return authRepositoryImpl.isFirstStart()
    }
}