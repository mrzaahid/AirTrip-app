package com.binarfp.airtrip.data.local.preference

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreDataSource @Inject constructor(private val dataStoreManager: DataStoreManager){
    suspend fun setAccessToken(token:String){
        dataStoreManager.setAccessToken(token)
    }
    fun getAccessToken():Flow<String>{
        return dataStoreManager.accessToken
    }
}