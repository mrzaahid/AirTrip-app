package com.binarfp.airtrip.data.network

import com.binarfp.airtrip.model.ResponseLogin
import com.binarfp.airtrip.model.ResponseRegist
import com.binarfp.airtrip.model.User
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

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
}