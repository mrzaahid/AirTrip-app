package com.binarfp.airtrip.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseRegist(
    @SerializedName("email")
    val email :String?
):Serializable
