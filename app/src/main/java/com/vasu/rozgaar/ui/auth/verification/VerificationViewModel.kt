package com.vasu.rozgaar.ui.auth.verification


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.vasu.rozgaar.data.repository.AuthRepository
import kotlinx.coroutines.*

class VerificationViewModel(private val authRepository: AuthRepository) :ViewModel() {
    var response = MutableLiveData<Boolean>()
    var firebaseUser = MutableLiveData<FirebaseUser>()
    private var job : Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception Handled: ${throwable.localizedMessage}")
    }

    init {
        authRepository.getAuthStatus().observeForever{
            response.postValue(it);
        }
    }
    fun signInWithFirebase(credential : PhoneAuthCredential) {
        job = CoroutineScope(Dispatchers.IO).launch {
            authRepository.signIn(credential)
        }
    }
    fun getCurrentUser(){
        job = CoroutineScope(Dispatchers.IO).launch {
          val user =  authRepository.getUser()
            firebaseUser.postValue(user)
        }
    }
    private fun onError(message: String) {
    }
}