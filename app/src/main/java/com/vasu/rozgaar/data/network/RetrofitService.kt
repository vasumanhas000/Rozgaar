package com.vasu.rozgaar.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    var retrofitService : RetrofitService ? = null
    fun getInstance() : RetrofitService{
        if (retrofitService==null){
            val retrofit = Retrofit.Builder().baseUrl("https://5e510330f2c0d300147c034c.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            retrofitService = retrofit.create(RetrofitService::class.java)
        }
        return retrofitService!!
    }
}