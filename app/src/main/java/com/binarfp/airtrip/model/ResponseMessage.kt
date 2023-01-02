package com.binarfp.airtrip.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseMessage(
    @SerializedName("status")
    val status : String,
    @SerializedName("message")
    val message:String
):Serializable
