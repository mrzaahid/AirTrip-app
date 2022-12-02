package com.binarfp.airtrip.data

import com.binarfp.airtrip.data.network.AirTripDataSource
import com.binarfp.airtrip.model.ResponseLogin
import com.binarfp.airtrip.model.ResponseRegist
import okhttp3.RequestBody
import retrofit2.Response

class LocalRepository(
    private val airTripDataSource: AirTripDataSource
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
}