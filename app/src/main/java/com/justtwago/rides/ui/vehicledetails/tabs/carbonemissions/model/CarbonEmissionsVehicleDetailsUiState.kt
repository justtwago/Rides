package com.justtwago.rides.ui.vehicledetails.tabs.carbonemissions.model

sealed interface CarbonEmissionsVehicleDetailsUiState {
    data object Initial : CarbonEmissionsVehicleDetailsUiState
    data class Details(
        val kilometrage: String,
        val carbonEmissions: String
    ) : CarbonEmissionsVehicleDetailsUiState
}