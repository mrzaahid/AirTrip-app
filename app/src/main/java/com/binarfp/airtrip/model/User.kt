package com.binarfp.airtrip.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class ResponseUpdateUser(
    @SerializedName("data")
    val data : User,
    @SerializedName("accessToken")
    val accessToken :String
)

data class ResponseUser(
    @SerializedName("data")
    val data: User
):Serializable
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
    @SerializedName("saldo")
    val saldo : Int,
    @SerializedName("role")
    val role : Role
):Serializable
data class Role(
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String
):Serializable
