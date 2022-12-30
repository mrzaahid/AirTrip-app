package com.binarfp.airtrip.model


import com.google.gson.annotations.SerializedName

data class History(
    @SerializedName("data")
    val data: List<HistoryData>?
)
data class HistoryData(
    @SerializedName("boardingPasses")
    val boardingPasses: BoardingPasses?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("flight_type")
    val flightType: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("invoice_number")
    val invoiceNumber: String?,
    @SerializedName("passenger")
    val passenger: Passenger?,
    @SerializedName("total_price")
    val totalPrice: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)