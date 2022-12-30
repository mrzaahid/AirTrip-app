package com.binarfp.airtrip.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BoardingPassDetail(
    @SerializedName("flight")
    val flight: Flight?,
    @SerializedName("seat")
    val seat: String?
):Serializable