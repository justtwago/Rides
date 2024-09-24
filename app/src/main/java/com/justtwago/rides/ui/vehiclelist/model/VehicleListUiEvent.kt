package com.justtwago.rides.ui.vehiclelist.model

import com.justtwago.rides.ui.model.VehicleNavArgument

sealed interface VehicleListUiEvent {
    data class NavigateToVehicleDetails(
        val vehicleNavArgument: VehicleNavArgument
    ) : VehicleListUiEvent
}
