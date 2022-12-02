package com.binarfp.airtrip.di

import android.content.Context
import com.binarfp.airtrip.data.local.preference.DataStoreDataSource
import com.binarfp.airtrip.data.local.preference.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideUserPreferenceDataStore(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)

    }
    @Singleton
    @Provides
    fun provideUserDataStoreDataSource(userDataStoreManager: DataStoreManager): DataStoreDataSource {
        return DataStoreDataSource(userDataStoreManager)
    }
}