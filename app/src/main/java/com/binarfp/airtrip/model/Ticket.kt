package com.binarfp.airtrip.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class responsTicket(
    @SerializedName("data")
    val data : Ticket
):Serializable

data class Ticket(
    @SerializedName("boardingPasses")
    val boardingPasses: BoardingPasses?,
    @SerializedName("flightType")
    val flightType: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("invoiceNumber")
    val invoiceNumber: String?,
    @SerializedName("totalPrice")
    val totalPrice: Int?,
    @SerializedName("username")
    val username: String?
):Serializable