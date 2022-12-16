package com.binarfp.airtrip.model

import com.google.gson.annotations.SerializedName

data class ResponseGetAirport(
    @SerializedName("data")
    val data : List<DataAirport>
)
data class DataAirport(
    @SerializedName("id")
    val id : Int,
    @SerializedName("iata")
    val iata : String,
    @SerializedName("name")
    val name : String,
    @SerializedName("address")
    val address : String
)