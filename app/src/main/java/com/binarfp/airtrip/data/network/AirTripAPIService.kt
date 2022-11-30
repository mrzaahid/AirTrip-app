package com.binarfp.airtrip.data.network

import com.binarfp.airtrip.model.User
import retrofit2.http.GET
import retrofit2.http.Query

interface AirTripAPIService {
    @GET("/register")
    suspend fun register(
        @Query("name")name : String,
        @Query("image")image : String?,
        @Query("phone")phone : Int,
        @Query("address")address :String,
        @Query("email")email : String,
        @Query("password")password : String,
    ):User
}