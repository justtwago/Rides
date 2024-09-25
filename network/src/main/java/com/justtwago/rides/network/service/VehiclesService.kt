package com.justtwago.rides.network.service

import com.justtwago.rides.network.model.VehicleResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface VehiclesService {

    @GET("vehicle/random_vehicle")
    suspend fun getVehicles(@Query("size") size: Int): List<VehicleResponse>
}