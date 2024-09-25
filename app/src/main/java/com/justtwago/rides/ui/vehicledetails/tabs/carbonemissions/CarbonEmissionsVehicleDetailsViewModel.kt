package com.justtwago.rides.ui.vehicledetails.tabs.carbonemissions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.justtwago.rides.ui.vehicledetails.tabs.carbonemissions.model.CarbonEmissionsVehicleDetailsUiState
import com.justtwago.rides.ui.vehicledetails.tabs.carbonemissions.util.CarbonEmissionsCalculator
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = CarbonEmissionsVehicleDetailsViewModel.Factory::class)
class CarbonEmissionsVehicleDetailsViewModel @AssistedInject constructor(
    @Assisted private val kilometrage: Int,
    private val emissionsCalculator: CarbonEmissionsCalculator
) : ViewModel() {

    val uiState = flow {
        emit(
            CarbonEmissionsVehicleDetailsUiState.Details(
                kilometrage.toString(),
                emissionsCalculator.calculateEmissions(kilometrage).toString()
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = CarbonEmissionsVehicleDetailsUiState.Initial
    )

    @AssistedFactory
    interface Factory {
        fun create(kilometrage: Int): CarbonEmissionsVehicleDetailsViewModel
    }
}