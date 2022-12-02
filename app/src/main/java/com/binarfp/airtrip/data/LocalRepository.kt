package com.binarfp.airtrip.data

import com.binarfp.airtrip.data.local.preference.DataStoreDataSource
import com.binarfp.airtrip.data.network.AirTripDataSource
import com.binarfp.airtrip.model.ResponseLogin
import com.binarfp.airtrip.model.ResponseRegist
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
    suspend fun setAccessToken(token:String){
        dataStoreDataSource.setAccessToken(token)
    }
    fun getAccessToken():Flow<String>{
        return dataStoreDataSource.getAccessToken()
    }
}