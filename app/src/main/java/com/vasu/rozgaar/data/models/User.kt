package com.vasu.rozgaar.data.models

import com.google.gson.annotations.SerializedName

class User (
    @SerializedName("name") var name:String,
    @SerializedName("phone") var phone:Number,
    @SerializedName("isOrganization") var isOrganization : Boolean,
    @SerializedName("organizationName") var organization : String?
)