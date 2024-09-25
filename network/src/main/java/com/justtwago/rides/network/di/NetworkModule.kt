package com.justtwago.rides.network.di

import com.justtwago.rides.domain.repository.VehiclesRepository
import com.justtwago.rides.network.VehiclesNetworkRepository
import com.justtwago.rides.network.service.VehiclesService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl("https://random-data-api.com/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()


    @Provides
    fun provideVehiclesService(retrofit: Retrofit): VehiclesService =
        retrofit.create(VehiclesService::class.java)

    @Provides
    fun provideVehiclesRepository(
        networkRepository: VehiclesNetworkRepository
    ): VehiclesRepository = networkRepository
}