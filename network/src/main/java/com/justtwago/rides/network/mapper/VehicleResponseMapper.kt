package com.justtwago.rides.network.mapper

import com.justtwago.rides.domain.model.Vehicle
import com.justtwago.rides.network.model.VehicleResponse
import javax.inject.Inject

internal class VehicleResponseMapper @Inject constructor() {

    fun mapToVehicleDomain(vehicleResponse: VehicleResponse): Vehicle = Vehicle(
        vin = vehicleResponse.vin,
        makeAndModel = vehicleResponse.makeAndModel,
        color = vehicleResponse.color,
        carType = vehicleResponse.carType,
        kilometrage = vehicleResponse.kilometrage
    )
}