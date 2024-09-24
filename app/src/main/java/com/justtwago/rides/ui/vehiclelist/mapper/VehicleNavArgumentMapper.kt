package com.justtwago.rides.ui.vehiclelist.mapper

import com.justtwago.rides.domain.model.Vehicle
import com.justtwago.rides.ui.model.VehicleNavArgument
import javax.inject.Inject

class VehicleNavArgumentMapper @Inject constructor() {

    fun mapToVehicleNavArgument(vehicle: Vehicle): VehicleNavArgument = VehicleNavArgument(
        vin = vehicle.vin,
        makeAndModel = vehicle.makeAndModel,
        color = vehicle.color,
        carType = vehicle.carType
    )
}
