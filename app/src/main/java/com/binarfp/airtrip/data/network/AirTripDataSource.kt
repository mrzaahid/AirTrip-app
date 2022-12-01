package com.binarfp.airtrip.data.network

import com.binarfp.airtrip.model.User

class AirTripDataSource(private val api : AirTripAPIService) {
    suspend fun register(
        name : String,
        image : String,
        phone : Int,
        email : String,
        address : String,
        password : String,
        role_id : Int
    ) : User {
        return api.register(name,image,phone,address,email,password,role_id)
    }
}