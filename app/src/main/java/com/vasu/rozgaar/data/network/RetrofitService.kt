package com.vasu.rozgaar.data.network

import com.vasu.rozgaar.data.models.User
import com.vasu.rozgaar.util.Constants.Companion.Base_URL
import com.vasu.rozgaar.util.NullOnEmptyConverterFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitService{
    @Headers("Accept: application/json")
    @GET("users/myProfile")
    suspend fun getUser(@HeaderMap headers: Map<String,String>) : Response<User>
    @Headers("Accept: application/json")
    @POST("users")
    suspend fun postUser(@HeaderMap headers: Map<String, String>, @Body user:User) : Response<User>
    companion object{
    var retrofitService : RetrofitService ? = null
    fun getInstance() : RetrofitService{
        if (retrofitService==null){
            val retrofit = Retrofit.Builder().baseUrl(Base_URL)
                .addConverterFactory(NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            retrofitService = retrofit.create(RetrofitService::class.java)
        }
        return retrofitService!!
    }
    }
}