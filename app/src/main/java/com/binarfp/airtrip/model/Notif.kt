package com.binarfp.airtrip.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponsesNotif(
    @SerializedName("data")
    val data : List<Notif>
):Serializable
data class Notif(
    @SerializedName("id")
    val id :Int,
    @SerializedName("user_id")
    val userId:Int,
    @SerializedName("has_read")
    val hasRead:Boolean,
    @SerializedName("ticket_id")
    val ticketId :Int,
    @SerializedName("message")
    val message :String,
    @SerializedName("createdAt")
    val createdAt:String,
    @SerializedName("updatedAt")
    val updatedAt:String
):Serializable