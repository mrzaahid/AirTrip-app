package com.binarfp.airtrip.data.network

import com.binarfp.airtrip.model.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface AirTripAPIService {
    @POST("/register")
    suspend fun register(
        @Body requestBody: RequestBody
//    ): ResponseRegist
    ): Response<ResponseRegist>
    @POST("/login")
    suspend fun login(
        @Body requestBody: RequestBody
//    ): ResponseRegist
    ):Response<ResponseLogin>

    @GET("/airports")
    suspend fun getAirports() : ResponseGetAirport

    @GET("/flights")
    suspend fun getFlights() : responseFlight

    @POST("/tickets/create")
    suspend fun createTickets(
        @Header("Authorization")token:String,
        @Body requestBody: RequestBody
    ):responsTicket

    @GET("/tickets/history")
    suspend fun getHistory(
        @Header("Authorization")token: String,
    ):History
    @GET("/whoami")
    suspend fun getProfile(
        @Header("Authorization")token:String
    )
}