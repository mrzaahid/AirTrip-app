package com.binarfp.airtrip.data

import com.binarfp.airtrip.data.local.preference.DataStoreDataSource
import com.binarfp.airtrip.data.local.preference.DataStoreManager
import com.binarfp.airtrip.data.network.AirTripAPIService
import com.binarfp.airtrip.data.network.AirTripDataSource
import com.binarfp.airtrip.model.ResponseGetAirport
import com.binarfp.airtrip.model.ResponseLogin
import com.binarfp.airtrip.model.ResponseRegist
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import okhttp3.RequestBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class LocalRepositoryTest {
    private lateinit var localRepository: LocalRepository
    private lateinit var airTripDataSource: AirTripDataSource
    private lateinit var dataStoreDataSource: DataStoreDataSource
    private lateinit var service : AirTripAPIService
    private lateinit var dataSource: DataStoreManager

    @Before
    fun setUp() {
        service = mockk()
        dataSource = mockk()
        airTripDataSource = AirTripDataSource(service)
        dataStoreDataSource = DataStoreDataSource(dataSource)
        localRepository = LocalRepository(airTripDataSource,dataStoreDataSource)
    }

    @Test
    fun registrasi() {
        val response = mockk<Response<ResponseRegist>>()
        val bodyregist = mockk<RequestBody>()
        every {
            runBlocking {
                service.register(bodyregist)
            }
        }returns response

        val result = runBlocking {
            localRepository.registrasi(bodyregist)
        }
        Truth.assertThat(result).isEqualTo(response)
    }

    @Test
    fun login() {
        val response = mockk<Response<ResponseLogin>>()
        val bodyregist = mockk<RequestBody>()
        every {
            runBlocking {
                service.login(bodyregist)
            }
        }returns response

        val result = runBlocking {
            localRepository.login(bodyregist)
        }
        Truth.assertThat(result).isEqualTo(response)
    }

    @Test
    fun setAccessToken() {

    }

    @Test
    fun getAccessToken() {
        val response = mockk<Flow<String>>()
        every {
            runBlocking {
                dataSource.accessToken
            }
        }returns response

        val result = runBlocking {
            localRepository.getAccessToken()
        }
        Truth.assertThat(result).isEqualTo(response)
    }

    @Test
    fun getAirports() {
        val response = mockk<ResponseGetAirport>()
        every {
            runBlocking {
                service.getAirports()
            }
        }returns response

        val result = runBlocking {
            localRepository.getAirports()
        }
        Truth.assertThat(result).isEqualTo(response)
    }


}