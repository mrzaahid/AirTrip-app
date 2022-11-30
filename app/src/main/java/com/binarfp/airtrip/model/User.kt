package com.binarfp.airtrip.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class User(
    @SerializedName("id")
    val id : Int?,
    @SerializedName("name")
    val name : String,
    @SerializedName("image")
    val image : String?,
    @SerializedName("phone")
    val phone : Int?,
    @SerializedName("address")
    val address : String,
    @SerializedName("email")
    val email : String,
    @SerializedName("password")
    val password : String,
    @SerializedName("createdAt")
    val createdAt : Date,
    @SerializedName("updatedAt")
    val updatedAt : Date,
    @SerializedName("role_id")
    val role_id : Int
)
