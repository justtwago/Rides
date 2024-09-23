package com.justtwago.rides.network

import com.justtwago.rides.domain.model.Vehicle
import com.justtwago.rides.domain.repository.VehiclesRepository
import com.justtwago.rides.network.mapper.VehicleResponseMapper
import com.justtwago.rides.network.service.VehiclesService
import javax.inject.Inject

internal class VehiclesNetworkRepository @Inject constructor(
    private val service: VehiclesService,
    private val vehicleResponseMapper: VehicleResponseMapper
) : VehiclesRepository {

    override suspend fun getVehicles(size: Int): List<Vehicle> {
        return service.getVehicles(size = size)
            .map(vehicleResponseMapper::mapToVehicleDomain)
    }
}
