package com.justtwago.rides.ui.vehiclelist

import app.cash.turbine.test
import com.justtwago.rides.domain.model.Vehicle
import com.justtwago.rides.domain.repository.VehiclesRepository
import com.justtwago.rides.ui.model.VehicleNavArgument
import com.justtwago.rides.ui.mapper.VehicleNavArgumentMapper
import com.justtwago.rides.ui.vehiclelist.model.SearchValidationError
import com.justtwago.rides.ui.vehiclelist.model.VehicleListUiEvent
import com.justtwago.rides.ui.vehiclelist.model.VehicleListUiState
import com.justtwago.rides.ui.vehiclelist.model.VehicleSorting.CAR_TYPE
import com.justtwago.rides.ui.vehiclelist.model.VehicleSorting.VIN
import com.justtwago.rides.ui.vehiclelist.model.VehiclesState
import com.justtwago.rides.ui.vehiclelist.util.VehicleCountValidator
import com.justtwago.rides.ui.vehiclelist.util.VehiclesSorter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class VehicleListViewModelTest {
    @MockK
    private lateinit var repository: VehiclesRepository

    @MockK
    private lateinit var vehiclesSorter: VehiclesSorter

    @MockK
    private lateinit var vehicleNavArgumentMapper: VehicleNavArgumentMapper

    private lateinit var vehicleCountValidator: VehicleCountValidator

    private lateinit var viewModel: VehicleListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        vehicleCountValidator = VehicleCountValidator()
        viewModel = VehicleListViewModel(
            repository = repository,
            vehiclesSorter = vehiclesSorter,
            vehicleNavArgumentMapper = vehicleNavArgumentMapper,
            vehicleCountValidator = vehicleCountValidator
        )
    }

    @Test
    fun `GIVEN two vehicles WHEN onSearchVehiclesClicked is called THEN emit loading state and then content state with sorted vehicles`() =
        runTest {
            // GIVEN
            val sorting = VIN
            val query = "2"
            val size = 2
            coEvery { repository.getVehicles(size) } returns vehicles
            every { vehiclesSorter.sortVehicles(vehicles, sorting) } returns vehicles.reversed()

            // WHEN
            viewModel.onSearchVehiclesClicked(query)

            // THEN
            viewModel.uiState.test {
                assertEquals(VehicleListUiState.Initial, awaitItem())
                assertEquals(
                    VehicleListUiState(
                        sorting = VIN,
                        vehicles = VehiclesState.Loading
                    ),
                    awaitItem()
                )
                assertEquals(
                    VehicleListUiState(
                        sorting = VIN,
                        vehicles = VehiclesState.Content(vehicles.reversed())
                    ),
                    awaitItem()
                )
            }
        }

    @Test
    fun `GIVEN empty query WHEN onSearchVehiclesClicked is called THEN emit state with Empty error`() =
        runTest {
            // GIVEN
            val query = ""

            // WHEN
            viewModel.onSearchVehiclesClicked(query)

            // THEN
            viewModel.uiState.test {
                assertEquals(VehicleListUiState.Initial, awaitItem())
                assertEquals(
                    VehicleListUiState(
                        sorting = VIN,
                        vehicles = VehiclesState.Content.Empty,
                        searchValidationError = SearchValidationError.Empty
                    ),
                    awaitItem()
                )
            }
        }

    @Test
    fun `GIVEN query less than one WHEN onSearchVehiclesClicked is called THEN emit state with LessThanOne error`() =
        runTest {
            // GIVEN
            val query = "0"

            // WHEN
            viewModel.onSearchVehiclesClicked(query)

            // THEN
            viewModel.uiState.test {
                assertEquals(VehicleListUiState.Initial, awaitItem())
                assertEquals(
                    VehicleListUiState(
                        sorting = VIN,
                        vehicles = VehiclesState.Content.Empty,
                        searchValidationError = SearchValidationError.LessThanOne
                    ),
                    awaitItem()
                )
            }
        }

    @Test
    fun `GIVEN query greater than hundred WHEN onSearchVehiclesClicked is called THEN emit state with GreaterThanHundred error`() =
        runTest {
            // GIVEN
            val query = "101"

            // WHEN
            viewModel.onSearchVehiclesClicked(query)

            // THEN
            viewModel.uiState.test {
                assertEquals(VehicleListUiState.Initial, awaitItem())
                assertEquals(
                    VehicleListUiState(
                        sorting = VIN,
                        vehicles = VehiclesState.Content.Empty,
                        searchValidationError = SearchValidationError.GreaterThanHundred
                    ),
                    awaitItem()
                )
            }
        }

    @Test
    fun `GIVEN query not a number WHEN onSearchVehiclesClicked is called THEN emit state with NotANumber error`() =
        runTest {
            // GIVEN
            val query = "abc"

            // WHEN
            viewModel.onSearchVehiclesClicked(query)

            // THEN
            viewModel.uiState.test {
                assertEquals(VehicleListUiState.Initial, awaitItem())
                assertEquals(
                    VehicleListUiState(
                        sorting = VIN,
                        vehicles = VehiclesState.Content.Empty,
                        searchValidationError = SearchValidationError.NotANumber
                    ),
                    awaitItem()
                )
            }
        }

    @Test
    fun `GIVEN error on fetching vehicles WHEN onSearchVehiclesClicked is called THEN emit loading state and then content state with empty list`() =
        runTest {
            // GIVEN
            val query = "2"
            val size = 2

            coEvery { repository.getVehicles(size) } throws IllegalStateException()

            // WHEN
            viewModel.onSearchVehiclesClicked(query)

            // THEN
            viewModel.uiState.test {
                assertEquals(VehicleListUiState.Initial, awaitItem())
                assertEquals(
                    VehicleListUiState(
                        sorting = VIN,
                        vehicles = VehiclesState.Loading
                    ),
                    awaitItem()
                )
                assertEquals(
                    VehicleListUiState(
                        sorting = VIN,
                        vehicles = VehiclesState.Content(emptyList())
                    ),
                    awaitItem()
                )
            }
        }

    @Test
    fun `GIVEN a vehicle WHEN onVehicleClicked is called THEN emit NavigateToVehicleDetails event`() =
        runTest {
            // GIVEN
            val vehicle = Vehicle(
                vin = "123",
                makeAndModel = "Toyota Camry",
                color = "Red",
                carType = "Sedan",
                kilometrage = 45678
            )
            val vehicleNavArgument = VehicleNavArgument(
                vin = "123",
                makeAndModel = "Toyota Camry",
                color = "Red",
                carType = "Sedan",
                kilometrage = 45678
            )

            every {
                vehicleNavArgumentMapper.mapToVehicleNavArgument(vehicle)
            } returns vehicleNavArgument

            // WHEN
            viewModel.onVehicleClicked(vehicle)


            // THEN
            viewModel.uiEvents.test {
                assertEquals(
                    VehicleListUiEvent.NavigateToVehicleDetails(vehicleNavArgument),
                    awaitItem()
                )
            }
        }

    @Test
    fun `GIVEN a list of vehicles and a sorting WHEN onSortingSelected is called THEN update sorting in the state and sort the vehicles accordingly`() =
        runTest {
            // GIVEN
            val size = 2
            val query = "2"

            coEvery { repository.getVehicles(size) } returns vehicles
            every { vehiclesSorter.sortVehicles(vehicles, VIN) } returns vehicles
            every { vehiclesSorter.sortVehicles(vehicles, CAR_TYPE) } returns vehicles.reversed()
            viewModel.onSearchVehiclesClicked(query)

            // WHEN
            viewModel.onSortingSelected(CAR_TYPE)

            // THEN
            viewModel.uiState.test {
                assertEquals(VehicleListUiState.Initial, awaitItem())
                assertEquals(
                    VehicleListUiState(
                        sorting = VIN,
                        vehicles = VehiclesState.Loading
                    ),
                    awaitItem()
                )
                assertEquals(
                    VehicleListUiState(
                        sorting = VIN,
                        vehicles = VehiclesState.Content(vehicles)
                    ),
                    awaitItem()
                )
                assertEquals(
                    VehicleListUiState(
                        sorting = CAR_TYPE,
                        vehicles = VehiclesState.Content(vehicles.reversed())
                    ),
                    awaitItem()
                )
            }
        }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }
}

private val vehicles = listOf(
    Vehicle(
        vin = "123",
        makeAndModel = "Toyota Camry",
        color = "Red",
        carType = "Sedan",
        kilometrage = 45678
    ),
    Vehicle(
        vin = "456",
        makeAndModel = "Honda Civic",
        color = "Blue",
        carType = "Hatchback",
        kilometrage = 45678
    )
)