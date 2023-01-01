package com.binarfp.airtrip.data.network

import com.binarfp.airtrip.model.*
import okhttp3.RequestBody
import retrofit2.http.*

interface AirTripAPIService {
    @POST("/register")
    suspend fun register(
        @Body requestBody: RequestBody
//    ): ResponseRegist
    ): ResponseRegist
    @POST("/login")
    suspend fun login(
        @Body requestBody: RequestBody
//    ): ResponseRegist
    ):ResponseLogin

    @GET("/airports")
    suspend fun getAirports() : ResponseGetAirport

    @GET("/airports/{id}")
    suspend fun getAirportbyId(
        @Header("Authorization")token: String,
        @Path("id")id:Int
    ):DataAirport

    @GET("/flights")
    suspend fun getFlights() : ResponseFlight

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
    ):ResponseUser

    @PUT("/users/update/{id}")
    suspend fun updateUser(
        @Header("Authorization")token: String,
        @Path("id")id:Int,
        @Body requestBody: RequestBody
    ):ResponseUpdateUser

    @GET("/notifications")
    suspend fun getNotif(
        @Header("Authorization")token: String
    ):ResponsesNotif

}