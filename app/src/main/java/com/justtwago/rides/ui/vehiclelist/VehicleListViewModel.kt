package com.justtwago.rides.ui.vehiclelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.justtwago.rides.domain.model.Vehicle
import com.justtwago.rides.domain.repository.VehiclesRepository
import com.justtwago.rides.ui.mapper.VehicleNavArgumentMapper
import com.justtwago.rides.ui.vehiclelist.model.SearchValidationError
import com.justtwago.rides.ui.vehiclelist.model.VehicleListUiEvent
import com.justtwago.rides.ui.vehiclelist.model.VehicleListUiState
import com.justtwago.rides.ui.vehiclelist.model.VehicleSorting
import com.justtwago.rides.ui.vehiclelist.model.VehiclesState
import com.justtwago.rides.ui.vehiclelist.util.VehicleCountValidator
import com.justtwago.rides.ui.vehiclelist.util.VehicleCountValidator.VehicleCountValidationResult.Invalid
import com.justtwago.rides.ui.vehiclelist.util.VehicleCountValidator.VehicleCountValidationResult.Valid
import com.justtwago.rides.ui.vehiclelist.util.VehiclesSorter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleListViewModel @Inject constructor(
    private val repository: VehiclesRepository,
    private val vehiclesSorter: VehiclesSorter,
    private val vehicleNavArgumentMapper: VehicleNavArgumentMapper,
    private val vehicleCountValidator: VehicleCountValidator
) : ViewModel() {

    private val _uiState = MutableStateFlow(VehicleListUiState.Initial)
    val uiState: StateFlow<VehicleListUiState> = _uiState

    private val _uiEvents = MutableSharedFlow<VehicleListUiEvent>()
    val uiEvents: SharedFlow<VehicleListUiEvent> = _uiEvents

    fun onSearchVehiclesClicked(query: String) {
        val errorState = when (val result = vehicleCountValidator.isQueryValid(query)) {
            is Invalid.Empty -> SearchValidationError.Empty
            is Invalid.LessThanOne -> SearchValidationError.LessThanOne
            is Invalid.GreaterThanHundred -> SearchValidationError.GreaterThanHundred
            is Invalid.NotANumber -> SearchValidationError.NotANumber
            is Valid -> {
                fetchVehicles(result.count)
                null
            }
        }
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(searchValidationError = errorState))
        }
    }

    private fun fetchVehicles(vehicleCount: Int) {
        viewModelScope.launch {
            runCatching {
                _uiState.emit(uiState.value.copy(vehicles = VehiclesState.Loading))
                repository.getVehicles(vehicleCount)
            }.onSuccess { vehicles ->
                _uiState.emit(
                    uiState.value.copy(
                        vehicles = VehiclesState.Content(
                            vehicles = vehiclesSorter.sortVehicles(
                                vehicles,
                                uiState.value.sorting
                            )
                        )
                    )
                )
            }.onFailure {
                _uiState.emit(uiState.value.copy(vehicles = VehiclesState.Content.Empty))
            }
        }
    }

    fun onVehicleClicked(vehicle: Vehicle) {
        viewModelScope.launch {
            _uiEvents.emit(
                VehicleListUiEvent.NavigateToVehicleDetails(
                    vehicleNavArgument = vehicleNavArgumentMapper.mapToVehicleNavArgument(vehicle)
                )
            )
        }
    }

    fun onSortingSelected(sorting: VehicleSorting) {
        viewModelScope.launch {
            _uiState.emit(
                uiState.value.copy(
                    sorting = sorting,
                    vehicles = when (val vehiclesState = uiState.value.vehicles) {
                        is VehiclesState.Content -> {
                            VehiclesState.Content(
                                vehicles = vehiclesSorter.sortVehicles(
                                    vehiclesState.vehicles,
                                    sorting
                                )
                            )
                        }

                        else -> vehiclesState
                    }
                )
            )
        }
    }
}