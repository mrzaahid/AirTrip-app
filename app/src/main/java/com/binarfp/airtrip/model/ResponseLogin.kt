package com.binarfp.airtrip.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseLogin(
    @SerializedName("accessToken")
    val accessToken : String
):Serializable
