package com.binarfp.airtrip.model

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("accessToken")
    val accessToken : String
)
