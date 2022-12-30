package com.binarfp.airtrip.data.network

import com.binarfp.airtrip.model.*
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Inject

class AirTripDataSource @Inject constructor(private val api : AirTripAPIService) {
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

    suspend fun getFlights() : responseFlight{
        return api.getFlights()
    }
    suspend fun createTicket(
        token:String,
        requestBody: RequestBody
    ):responsTicket{
        return  api.createTickets(token,requestBody)
    }

    suspend fun getHistory(
        token: String
    ):History{
        return api.getHistory(token)
    }
}