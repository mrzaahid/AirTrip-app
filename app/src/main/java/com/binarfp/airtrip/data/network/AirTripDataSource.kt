package com.binarfp.airtrip.data.network

import com.binarfp.airtrip.model.ResponseGetAirport
import com.binarfp.airtrip.model.ResponseLogin
import com.binarfp.airtrip.model.ResponseRegist
import com.binarfp.airtrip.model.User
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body

class AirTripDataSource(private val api : AirTripAPIService) {
    suspend fun register(
        requestBody: RequestBody
//    ): ResponseRegist
    ): Response<ResponseRegist>{
        return api.register(requestBody)
    }
    suspend fun login(
        requestBody: RequestBody
//    ): ResponseRegist
    ):Response<ResponseLogin>{
        return api.login(requestBody)
    }
    suspend fun getAirports() : ResponseGetAirport{
        return api.getAirports()
    }

}