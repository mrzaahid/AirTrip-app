package com.binarfp.airtrip.data.local.preference

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(private val context: Context) {
    fun getPreference(context: Context): Flow<Preferences>{
        return context.userDataStore.data
    }
    suspend fun setAccessToken(token:String){
        context.userDataStore.edit {
            it[ACCESSTOKEN] = token
        }
    }
    val accessToken:Flow<String>
    get() = context.userDataStore.data.map {
        it[ACCESSTOKEN] ?: ""
    }
    companion object{
        private const val DATASTORE_NAME = "user_preference"
        private val ACCESSTOKEN = stringPreferencesKey("username")

        private val Context.userDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}