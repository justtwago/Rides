package com.justtwago.rides.network

import com.justtwago.rides.domain.model.Vehicle
import com.justtwago.rides.domain.repository.VehiclesRepository

internal class VehiclesNetworkRepository : VehiclesRepository {

    override suspend fun getVehicles(): List<Vehicle> {
        return emptyList()
    }
}