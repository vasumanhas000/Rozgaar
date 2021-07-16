package com.vasu.rozgaar.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesFunctions(private val context:Context) {
    private var sharedPreferences = context.getSharedPreferences("com.vasu.rozgaar",Context.MODE_PRIVATE)
    private var editor : SharedPreferences.Editor = sharedPreferences.edit()

    companion object{
        const val SHARED_PREFERENCES_USER_TYPE = "user_type"
        const val SHARED_PREFERENCES_PIN_CODE = "pin_code"
    }

    fun saveUserType(type:String){
        editor.putString(SHARED_PREFERENCES_USER_TYPE,type).commit()
    }

    fun getUserType():String?{
     return sharedPreferences.getString(SHARED_PREFERENCES_USER_TYPE,null)
    }

    fun savePincode(pincode : String){
        editor.putString(SHARED_PREFERENCES_PIN_CODE,pincode).commit()
    }

    fun getPincode(): String?{
        return sharedPreferences.getString(SHARED_PREFERENCES_PIN_CODE,null)
    }

}