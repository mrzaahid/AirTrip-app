package com.binarfp.airtrip.data

import com.binarfp.airtrip.data.local.preference.DataStoreDataSource
import com.binarfp.airtrip.data.network.AirTripDataSource
import com.binarfp.airtrip.model.*
import com.zaahid.challenge6.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val airTripDataSource: AirTripDataSource,
    private val dataStoreDataSource: DataStoreDataSource
) {

    suspend fun setAccessToken(token: String): Boolean {
        return dataStoreDataSource.setAccessToken(token)
    }

    fun getAccessToken(): Flow<String> {
        return dataStoreDataSource.getAccessToken()
    }

    suspend fun setImageString(sImage: String): Boolean {
        return dataStoreDataSource.setImageString(sImage)
    }

    fun getImageString(): Flow<String> {
        return dataStoreDataSource.getImageString()
    }

    suspend fun registrasi(
        requestBody: RequestBody
    ): Resource<ResponseRegist> {
        return proceed {
            airTripDataSource.register(requestBody)
        }
    }

    suspend fun login(
        requestBody: RequestBody
    ): Resource<ResponseLogin> {
        return proceed {
            airTripDataSource.login(requestBody)
        }
    }

    suspend fun getAirports(): Resource<ResponseGetAirport> {
        return proceed {
            airTripDataSource.getAirports()
        }
    }

    suspend fun getAirportbyId(
        token: String,
        id: Int
    ): DataAirport {
        return airTripDataSource.getAirportbyId(token, id)
    }

    suspend fun getFlights(): Resource<ResponseFlight> {
        return proceed {
            airTripDataSource.getFlights()
        }
    }

    suspend fun searchFLight(
        token: String,
        departureDate: String,
        from: Int,
        to: Int,
        flightClass: String
    ): Resource<ResponseFlight> {
        return proceed {
            airTripDataSource.searchFLights(token, departureDate, from, to, flightClass)
        }
    }

    //admin
    suspend fun createFlights(
        token: String,
        requestBody: RequestBody
    ):Resource<ResponseFlight>{
        return proceed{ airTripDataSource.createFlights(token, requestBody) }
    }
    //admin
    suspend fun updateFlight(
        token: String,
        id: Int,
        requestBody: RequestBody
    ):Resource<ResponseFlight>{
        return proceed { airTripDataSource.updateFlight(token,id,requestBody) }
    }
    //admin
    suspend fun deleteFlight(
        token: String,
        id: Int
    ):ResponseMessage{
        return airTripDataSource.deleteFlight(token,id)
    }

    suspend fun createTicket(
        token: String,
        requestBody: RequestBody
    ): Resource<responsTicket> {
        return proceed { airTripDataSource.createTicket(token, requestBody) }
    }

    suspend fun getHistory(
        token: String
    ): Resource<History> {
        return proceed { airTripDataSource.getHistory(token) }
    }

    //admin
    suspend fun getTickets(
        token: String
    ):Resource<History>{
        return proceed { airTripDataSource.getTickets(token) }
    }

    suspend fun getProfile(
        token: String
    ): Resource<ResponseUser> {
        return proceed { airTripDataSource.getProfile(token) }
    }

    suspend fun updateUser(
        token: String,
        id: Int,
        requestBody: RequestBody
    ): Resource<ResponseUpdateUser> {
        return proceed { airTripDataSource.updateUser(token, id, requestBody) }
    }

    suspend fun getNotif(
        token: String
    ): Resource<ResponsesNotif> {
        return proceed {
            airTripDataSource.getNotif(token)
        }
    }

    suspend fun getAirplane(
        token: String
    ):Resource<ResponseAirplane>{
        return proceed { airTripDataSource.getAirplane(token) }
    }

    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }
}