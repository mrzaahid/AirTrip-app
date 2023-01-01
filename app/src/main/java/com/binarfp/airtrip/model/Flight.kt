package com.binarfp.airtrip.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseFlight(
    @SerializedName("status")
    val status : String?,
    @SerializedName("data")
    val data : List<Flight>
):Serializable
data class Flight(
    @SerializedName("Airplane")
    val airplane: Airplane?,
    @SerializedName("arrival")
    val arrival: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("deletedAt")
    val deletedAt: Any?,
    @SerializedName("departure")
    val departure: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("flight_class")
    val flightClass: String?,
    @SerializedName("from_airport")
    val fromAirport: Airport?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("to_airport")
    val toAirport: Airport?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("from")
    val from : Int,
    @SerializedName("to")
    val to : Int
):Serializable