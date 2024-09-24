package com.justtwago.rides.ui.vehicledetails.model

import com.justtwago.rides.domain.model.Vehicle

sealed interface VehicleDetailsUiState {
    data object Initial : VehicleDetailsUiState
    data class Details(val vehicle: Vehicle) : VehicleDetailsUiState
}