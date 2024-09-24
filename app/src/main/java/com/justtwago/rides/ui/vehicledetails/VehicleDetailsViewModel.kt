package com.justtwago.rides.ui.vehicledetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.justtwago.rides.ui.mapper.VehicleNavArgumentMapper
import com.justtwago.rides.ui.model.VehicleNavArgument
import com.justtwago.rides.ui.vehicledetails.model.VehicleDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

private const val VEHICLE_ARG_KEY = "vehicle"

@HiltViewModel
class VehicleDetailsViewModel @Inject constructor(
    savedState: SavedStateHandle,
    vehicleNavArgumentMapper: VehicleNavArgumentMapper
) : ViewModel() {

    val uiState: StateFlow<VehicleDetailsUiState> = flow<VehicleNavArgument> {
        emit(savedState[VEHICLE_ARG_KEY]!!)
    }.map { argument ->
        VehicleDetailsUiState.Details(
            vehicle = vehicleNavArgumentMapper.mapToVehicleDomain(argument)
        )
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = VehicleDetailsUiState.Initial
    )
}