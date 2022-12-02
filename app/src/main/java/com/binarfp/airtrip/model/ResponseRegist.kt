package com.binarfp.airtrip.model

import com.google.gson.annotations.SerializedName

data class ResponseRegist(
    @SerializedName("email")
    val email :String?
)
