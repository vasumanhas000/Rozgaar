package com.vasu.rozgaar.data.network

import com.vasu.rozgaar.data.models.User
import com.vasu.rozgaar.util.Constants.Companion.Base_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Headers

interface RetrofitService{
    @Headers("Accept: application/json")
    @GET("users/myProfile")
    suspend fun getUser(@HeaderMap headers: Map<String,String>) : User
    companion object{
    var retrofitService : RetrofitService ? = null
    fun getInstance() : RetrofitService{
        if (retrofitService==null){
            val retrofit = Retrofit.Builder().baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            retrofitService = retrofit.create(RetrofitService::class.java)
        }
        return retrofitService!!
    }
    }
}