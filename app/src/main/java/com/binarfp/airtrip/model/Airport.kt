package com.binarfp.airtrip.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Airport(
    @SerializedName("address")
    val address: String?,
    @SerializedName("country_code")
    val countryCode: String?,
    @SerializedName("iata")
    val iata: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
):Serializable