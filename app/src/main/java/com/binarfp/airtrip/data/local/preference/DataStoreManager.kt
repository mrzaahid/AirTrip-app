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

    suspend fun setAccessToken(token:String):Boolean{
        context.userDataStore.edit {
            it[ACCESSTOKEN] = token
        }
        return true
    }
    val accessToken:Flow<String>
    get() = context.userDataStore.data.map {
        it[ACCESSTOKEN] ?: ""
    }
    suspend fun setRead(read:Int):Boolean{
        context.userDataStore.edit {
            it[READ] = read
        }
        return true
    }
    val read:Flow<Int>
    get() = context.userDataStore.data.map {
        it[READ] ?: 0
    }
    companion object{
        private const val DATASTORE_NAME = "user_preference"
        private val ACCESSTOKEN = stringPreferencesKey("username")
        private val READ = intPreferencesKey("read")

        private val Context.userDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}