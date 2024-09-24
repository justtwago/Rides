package com.justtwago.rides.ui.vehiclelist.model

import com.justtwago.rides.domain.model.Vehicle

data class VehicleListUiState(
    val vehicles: VehiclesState,
    val sorting: VehicleSorting
) {
    companion object {
        val Initial = VehicleListUiState(VehiclesState.Content(emptyList()), VehicleSorting.VIN)
    }
}

// Error handling is not implemented according to the requirement to handle errors silently.
sealed interface VehiclesState {
    data object Loading : VehiclesState
    data class Content(val vehicles: List<Vehicle>) : VehiclesState {
        companion object {
            val Empty = Content(emptyList())
        }
    }
}
