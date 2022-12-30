package com.binarfp.airtrip.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Passenger(
    @SerializedName("address")
    val address: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?
):Serializable