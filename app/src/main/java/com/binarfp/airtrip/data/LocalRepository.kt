package com.binarfp.airtrip.data

import com.binarfp.airtrip.data.local.preference.DataStoreDataSource
import com.binarfp.airtrip.data.network.AirTripDataSource
import com.binarfp.airtrip.model.*
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import retrofit2.Response

class LocalRepository(
    private val airTripDataSource: AirTripDataSource,
    private val dataStoreDataSource: DataStoreDataSource
) {
    suspend fun registrasi(
        requestBody: RequestBody
    ):Response<ResponseRegist>{
        return airTripDataSource.register(requestBody)
    }

    suspend fun login(
        requestBody: RequestBody
    ):Response<ResponseLogin>{
        return airTripDataSource.login(requestBody)
    }
    suspend fun setAccessToken(token:String):Boolean{
        return dataStoreDataSource.setAccessToken(token)
    }
    fun getAccessToken():Flow<String>{
        return dataStoreDataSource.getAccessToken()
    }
    suspend fun getAirports():ResponseGetAirport{
        return airTripDataSource.getAirports()
    }
    suspend fun getFlights():responseFlight{
        return airTripDataSource.getFlights()
    }
    suspend fun createTicket(
        token: String,
        requestBody: RequestBody
    ):responsTicket{
        return airTripDataSource.createTicket(token,requestBody)
    }
    suspend fun getHistory(
        token: String
    ):History{
        return airTripDataSource.getHistory(token)
    }

    suspend fun setRead(angka:Int):Boolean{
        return dataStoreDataSource.setRead(angka)
    }
    fun getRead():Flow<Int>{
        return dataStoreDataSource.getRead()
    }
}