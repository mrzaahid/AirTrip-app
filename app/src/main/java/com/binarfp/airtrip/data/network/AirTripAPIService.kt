package com.binarfp.airtrip.data.network

import com.binarfp.airtrip.model.User
import retrofit2.http.POST
import retrofit2.http.Query

interface AirTripAPIService {
    @POST("/register")
    suspend fun register(
        @Query("name")name : String,
        @Query("image")image : String?,
        @Query("phone")phone : Int,
        @Query("address")address :String,
        @Query("email")email : String,
        @Query("password")password : String,
        @Query("role_id")role_id : Int
    ):User
}