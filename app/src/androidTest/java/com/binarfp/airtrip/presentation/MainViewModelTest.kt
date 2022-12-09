package com.binarfp.airtrip.presentation

import androidx.lifecycle.LiveData
import com.binarfp.airtrip.data.network.AirTripAPIService
import com.binarfp.airtrip.model.ResponseRegist
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert
import kotlinx.coroutines.runBlocking
import okhttp3.RequestBody
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import retrofit2.Response

class MainViewModelTest{
//    private lateinit var service: AirTripAPIService
//    private lateinit var requestBody: RequestBody
//
//    @Before
//    fun setup() {
//        service = mockk()
//    }
//
//    @Test
//    fun getRegistResponse(): Unit = runBlocking{
//        val expected = mockk<ResponseRegist>()
//        every {
//            runBlocking {
//                service.register(requestBody)
//            }
//        } returns expected
//
//        val actual = service.register()
//        verify {
//            runBlocking {
//                service.register(requestBody)
//            }
//        }
//        Assert.assertEquals(expected,actual)
//
//    }
}