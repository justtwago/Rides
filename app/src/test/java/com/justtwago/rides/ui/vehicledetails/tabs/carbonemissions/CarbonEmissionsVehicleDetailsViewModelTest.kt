package com.justtwago.rides.ui.vehicledetails.tabs.carbonemissions

import app.cash.turbine.test
import com.justtwago.rides.ui.vehicledetails.tabs.carbonemissions.model.CarbonEmissionsVehicleDetailsUiState
import com.justtwago.rides.ui.vehicledetails.tabs.carbonemissions.util.CarbonEmissionsCalculator
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
class CarbonEmissionsVehicleDetailsViewModelTest {
    @MockK
    private lateinit var emissionsCalculator: CarbonEmissionsCalculator
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
    fun `GIVEN a kilometrage WHEN CarbonEmissionsVehicleDetailsViewModel is created THEN emit CarbonEmissionsVehicleDetailsUiState Details with calculated emissions`() =
        runTest {
            // GIVEN
            val kilometrage = 51000

            every { emissionsCalculator.calculateEmissions(kilometrage) } returns 51500.0

            // WHEN
            val viewModel = CarbonEmissionsVehicleDetailsViewModel(kilometrage, emissionsCalculator)

            // THEN
            viewModel.uiState.test {
                assertEquals(CarbonEmissionsVehicleDetailsUiState.Initial, awaitItem())
                assertEquals(
                    CarbonEmissionsVehicleDetailsUiState.Details(
                        kilometrage = kilometrage.toString(),
                        carbonEmissions = "51500.0"
                    ),
                    awaitItem()
                )
            }
        }
}