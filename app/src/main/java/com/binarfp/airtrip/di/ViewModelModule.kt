package com.binarfp.airtrip.di

import com.binarfp.airtrip.data.LocalRepository
import com.binarfp.airtrip.data.local.preference.DataStoreDataSource
import com.binarfp.airtrip.data.network.AirTripDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @ViewModelScoped
    @Provides
    fun provideRepository(
        airTripDataSource: AirTripDataSource,
        dataStoreDataSource: DataStoreDataSource
    ): LocalRepository {
        return LocalRepository(airTripDataSource,dataStoreDataSource)
    }
}