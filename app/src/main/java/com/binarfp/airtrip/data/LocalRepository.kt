package com.binarfp.airtrip.data

import com.binarfp.airtrip.data.local.preference.DataStoreDataSource
import com.binarfp.airtrip.data.network.AirTripDataSource
import com.binarfp.airtrip.model.*
import com.zaahid.challenge6.wrapper.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

class LocalRepository(
    private val airTripDataSource: AirTripDataSource,
    private val dataStoreDataSource: DataStoreDataSource
) {

    suspend fun setAccessToken(token:String):Boolean{
    return dataStoreDataSource.setAccessToken(token)
    }
    fun getAccessToken():Flow<String>{
        return dataStoreDataSource.getAccessToken()
    }
    suspend fun setImageString(sImage:String):Boolean{
        return dataStoreDataSource.setImageString(sImage)
    }
    fun getImageString():Flow<String>{
        return dataStoreDataSource.getImageString()
    }
    suspend fun registrasi(
        requestBody: RequestBody
    ):Resource<ResponseRegist>{
        return proceed {
            airTripDataSource.register(requestBody)
        }
    }

    suspend fun login(
        requestBody: RequestBody
    ):Resource<ResponseLogin>{
        return proceed {
            airTripDataSource.login(requestBody)
        }
    }

    suspend fun getAirports():Resource<ResponseGetAirport>{
        return proceed {
            airTripDataSource.getAirports()
        }
    }
    suspend fun getAirportbyId(
        token: String,
        id: Int
    ):DataAirport{
        return airTripDataSource.getAirportbyId(token,id)
    }
    suspend fun getFlights():Resource<ResponseFlight>{
        return proceed {
            airTripDataSource.getFlights()
        }
    }

    suspend fun createTicket(
        token: String,
        requestBody: RequestBody
    ):Resource<responsTicket>{
        return proceed { airTripDataSource.createTicket(token,requestBody) }
    }
    suspend fun getHistory(
        token: String
    ):Resource<History>{
        return proceed { airTripDataSource.getHistory(token) }
    }
    suspend fun getProfile(
        token: String
    ):Resource<ResponseUser>{
        return proceed { airTripDataSource.getProfile(token) }
    }
    suspend fun updateUser(
        token: String,
        id : Int,
        requestBody: RequestBody
    ):Resource<ResponseUpdateUser>{
        return proceed { airTripDataSource.updateUser(token,id,requestBody) }
    }
    suspend fun getNotif(
        token: String
    ):Resource<ResponsesNotif>{
        return proceed {
            airTripDataSource.getNotif(token)
        }
    }

    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }
}