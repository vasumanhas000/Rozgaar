package com.vasu.rozgaar.data.repository

import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions

class AuthRepository {
    private val auth = FirebaseAuth.getInstance();
    private val _response = MutableLiveData<Boolean>();
    private val _userResponse = MutableLiveData<Boolean>();
    suspend fun signIn(credential : PhoneAuthCredential){
       auth.signInWithCredential(credential).addOnCompleteListener(){task ->
           if(task.isSuccessful){
               Log.i("auth","success");
               val user = task.result?.user
               _response.postValue(true);
           }else{
               Log.i("auth","error");
               if(task.exception is FirebaseAuthInvalidCredentialsException){
//                   Wrong code
                   _response.postValue(false)
               }

           }
       }
    }

    fun getAuthStatus():LiveData<Boolean>{
        return _response
    }

}