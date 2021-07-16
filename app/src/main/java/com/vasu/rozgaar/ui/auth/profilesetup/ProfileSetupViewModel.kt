package com.vasu.rozgaar.ui.auth.profilesetup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vasu.rozgaar.data.models.User
import com.vasu.rozgaar.data.repository.AuthRepository
import kotlinx.coroutines.*

class ProfileSetupViewModel(private val authRepository: AuthRepository) : ViewModel(){
    var successfulRequest = MutableLiveData<Boolean>()
    var userObject = MutableLiveData<User>()
    private val PROFILE_SETUP_VIEW_MODEL = "profilesetupviewmodel"
    private var job : Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception Handled: ${throwable.localizedMessage}")
    }

    fun postUser(headers:Map<String,String>,user: User){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            Log.i(PROFILE_SETUP_VIEW_MODEL,"started post user")
            val response = authRepository.postUser(headers,user)
            withContext(Dispatchers.Main){
            if(response.isSuccessful){
               userObject.postValue(response.body())
                successfulRequest.postValue(true)
            }else{
                successfulRequest.postValue(false)
                 }
            }
        }
    }
    private fun onError(message: String) {
        Log.i(PROFILE_SETUP_VIEW_MODEL,"Exception Handler - " +message)
    }
}