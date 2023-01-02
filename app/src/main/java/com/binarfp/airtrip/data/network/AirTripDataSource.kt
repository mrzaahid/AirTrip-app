package com.binarfp.airtrip.data.network

import com.binarfp.airtrip.model.*
import okhttp3.RequestBody
import javax.inject.Inject

class AirTripDataSource @Inject constructor(private val api: AirTripAPIService) {
    suspend fun register(
        requestBody: RequestBody
//    ): ResponseRegist
    ): ResponseRegist {
        return api.register(requestBody)
    }

    suspend fun login(
        requestBody: RequestBody
//    ): ResponseRegist
    ): ResponseLogin {
        return api.login(requestBody)
    }

    suspend fun getAirports(): ResponseGetAirport {
        return api.getAirports()
    }


    suspend fun getFlights(): ResponseFlight {
        return api.getFlights()
    }

    suspend fun searchFLights(
        token: String,
        departureDate: String,
        from: Int,
        to: Int,
        flightClass: String
    ): ResponseFlight {
        return api.searchFlights(token, departureDate, from, to, flightClass)
    }

    //admin
    suspend fun createFlights(
        token: String,
        requestBody: RequestBody
    ): ResponseFlight {
        return api.createFlights(token, requestBody)
    }

    //admin
    suspend fun updateFlight(
        token: String,
        id: Int,
        requestBody: RequestBody
    ): ResponseFlight {
        return api.updateflight(token, id,requestBody)
    }
    //admin
    suspend fun deleteFlight(
        token: String,
        id: Int
    ): ResponseMessage {
        return api.deleteFlight(token, id)
    }

    suspend fun createTicket(
        token: String,
        requestBody: RequestBody
    ): responsTicket {
        return api.createTickets(token, requestBody)
    }

    suspend fun getHistory(
        token: String
    ): History {
        return api.getHistory(token)
    }

    //admin
    suspend fun getTickets(
        token: String
    ): History {
        return api.getTickets(token)
    }

    suspend fun getProfile(
        token: String
    ): ResponseUser {
        return api.getProfile(token)
    }

    suspend fun updateUser(
        token: String,
        id: Int,
        requestBody: RequestBody
    ): ResponseUpdateUser {
        return api.updateUser(token, id, requestBody)
    }

    suspend fun getAirportbyId(
        token: String,
        id: Int
    ): DataAirport {
        return api.getAirportbyId(token, id)
    }

    suspend fun getNotif(
        token: String
    ): ResponsesNotif {
        return api.getNotif(token)
    }

    suspend fun getAirplane(
        token: String
    ):ResponseAirplane{
        return api.getAirplanes(token)
    }
}