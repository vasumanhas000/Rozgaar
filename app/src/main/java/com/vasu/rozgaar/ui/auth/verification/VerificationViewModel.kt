package com.vasu.rozgaar.ui.auth.verification


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.vasu.rozgaar.data.repository.AuthRepository
import kotlinx.coroutines.*

class VerificationViewModel(private val authRepository: AuthRepository) :ViewModel() {
    var response = MutableLiveData<Boolean>()
    var firebaseUser = MutableLiveData<FirebaseUser>()
    var profileCreated = MutableLiveData<Boolean>()
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
    fun checkUser(headers : Map<String,String>){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            Log.i("check","here");
           val response = authRepository.checkUser(headers)
           withContext(Dispatchers.Main){
               if(response.isSuccessful){
                   if(response.code()==204){
                       profileCreated.postValue(false)
                   }
                   else{
                       profileCreated.postValue(true)
                       Log.i("retrofit success",response.code().toString())
                   }
               }else{
                       Log.i("retrofit fail",response.errorBody().toString())
               }
           }
        }
    }
    private fun onError(message: String) {
        Log.i("error",message)
    }
}