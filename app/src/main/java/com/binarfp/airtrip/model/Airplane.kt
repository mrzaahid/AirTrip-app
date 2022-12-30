package com.binarfp.airtrip.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Airplane(
    @SerializedName("capacity")
    val capacity: Int?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("manufacture")
    val manufacture: String?,
    @SerializedName("model_number")
    val modelNumber: String?,
    @SerializedName("specs")
    val specs: List<String?>?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("deletedAt")
    val deletedAd : String?
):Serializable