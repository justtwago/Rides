package com.justtwago.rides.ui.vehicledetails

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.justtwago.rides.domain.model.Vehicle
import com.justtwago.rides.ui.mapper.VehicleNavArgumentMapper
import com.justtwago.rides.ui.model.VehicleNavArgument
import com.justtwago.rides.ui.vehicledetails.model.VehicleDetailsUiState
import io.mockk.MockKAnnotations
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
class VehicleDetailsViewModelTest {
    @MockK
    private lateinit var vehicleNavArgumentMapper: VehicleNavArgumentMapper
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN a VehicleNavArgument WHEN VehicleDetailsViewModel is created THEN emit VehicleDetailsUiState Details`() =
        runTest {
            // GIVEN
            val vehicleNavArgument = VehicleNavArgument(
                vin = "123",
                makeAndModel = "Toyota Camry",
                color = "Red",
                carType = "Sedan",
                kilometrage = 45678
            )

            val vehicle = Vehicle(
                vin = "123",
                makeAndModel = "Toyota Camry",
                color = "Red",
                carType = "Sedan",
                kilometrage = 45678
            )

            val savedStateHandle = SavedStateHandle()
            savedStateHandle["vehicle"] = vehicleNavArgument

            every {
                vehicleNavArgumentMapper.mapToVehicleDomain(vehicleNavArgument)
            } returns vehicle

            // WHEN
            val viewModel = VehicleDetailsViewModel(savedStateHandle, vehicleNavArgumentMapper)

            // THEN
            viewModel.uiState.test {
                assertEquals(VehicleDetailsUiState.Initial, awaitItem())
                assertEquals(VehicleDetailsUiState.Details(vehicle), awaitItem())
            }
        }
}
