package com.vasu.rozgaar.ui.auth.verification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.PhoneAuthCredential
import com.vasu.rozgaar.data.repository.AuthRepository

class VerificationViewModel(private val authRepository: AuthRepository) :ViewModel() {
    var response = MutableLiveData<Boolean>()
    val userResponse = MutableLiveData<Boolean>()

    init {
        authRepository.getAuthStatus().observeForever{
            response.postValue(it);
        }
    }
    suspend fun signInWithFirebase(credential : PhoneAuthCredential){
        authRepository.signIn(credential)
    }
}