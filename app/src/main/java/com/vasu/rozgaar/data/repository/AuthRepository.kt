package com.vasu.rozgaar.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.*
import com.vasu.rozgaar.data.models.User
import com.vasu.rozgaar.data.network.RetrofitService

class AuthRepository(private val retrofitService: RetrofitService) {
    private val auth = FirebaseAuth.getInstance();
    private val _response = MutableLiveData<Boolean>();
     fun signIn(credential : PhoneAuthCredential){
       auth.signInWithCredential(credential).addOnCompleteListener{task ->
           if(task.isSuccessful){
               Log.i("auth","success");
               val user =task.result?.user
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


     fun getUser(): FirebaseUser? {
        val user = auth.currentUser;
            return user
    }

    suspend fun checkUser(headers:Map<String,String>) = retrofitService.getUser(headers)

    suspend fun postUser(headers: Map<String, String>, user:User) = retrofitService.postUser(headers, user)


}