package com.justtwago.rides.ui.vehiclelist.model

import com.justtwago.rides.domain.model.Vehicle

data class VehicleListUiState(
    val vehicles: VehiclesState,
    val sorting: VehicleSorting,
    val searchValidationError: SearchValidationError? = null
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

sealed interface SearchValidationError {
    data object Empty : SearchValidationError
    data object NotANumber : SearchValidationError
    data object LessThanOne : SearchValidationError
    data object GreaterThanHundred : SearchValidationError
}
