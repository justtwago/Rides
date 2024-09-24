package com.justtwago.rides.ui.vehiclelist.util

import com.justtwago.rides.domain.model.Vehicle
import com.justtwago.rides.ui.vehiclelist.model.VehicleSorting
import javax.inject.Inject

class VehiclesSorter @Inject constructor() {

    fun sortVehicles(vehicles: List<Vehicle>, by: VehicleSorting): List<Vehicle> {
        return vehicles.sortedBy {
            when (by) {
                VehicleSorting.VIN -> it.vin
                VehicleSorting.CAR_TYPE -> it.carType
            }
        }
    }
}