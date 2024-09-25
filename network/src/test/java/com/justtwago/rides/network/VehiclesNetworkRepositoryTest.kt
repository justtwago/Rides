package com.justtwago.rides.network

import com.justtwago.rides.domain.model.Vehicle
import com.justtwago.rides.network.mapper.VehicleResponseMapper
import com.justtwago.rides.network.model.VehicleResponse
import com.justtwago.rides.network.service.VehiclesService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import junit.framework.TestCase.assertEquals

internal class VehiclesNetworkRepositoryTest {
    @MockK
    private lateinit var service: VehiclesService
    private lateinit var repository: VehiclesNetworkRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = VehiclesNetworkRepository(service, VehicleResponseMapper())
    }

    @Test
    fun `GIVEN service returns vehicles WHEN getVehicles is called THEN return mapped vehicles`() =
        runBlocking {
            // GIVEN
            val size = 2
            val vehicleResponses = listOf(
                VehicleResponse(
                    vin = "VIN1",
                    makeAndModel = "Model1",
                    color = "Red",
                    carType = "Sedan",
                    kilometrage = 45678
                ),
                VehicleResponse(
                    vin = "VIN2",
                    makeAndModel = "Model2",
                    color = "Blue",
                    carType = "SUV",
                    kilometrage = 45678
                )
            )
            coEvery { service.getVehicles(size) } returns vehicleResponses

            // WHEN
            val result = repository.getVehicles(size)

            // THEN
            assertEquals(
                listOf(
                    Vehicle(
                        vin = "VIN1",
                        makeAndModel = "Model1",
                        color = "Red",
                        carType = "Sedan",
                        kilometrage = 45678
                    ),
                    Vehicle(
                        vin = "VIN2",
                        makeAndModel = "Model2",
                        color = "Blue",
                        carType = "SUV",
                        kilometrage = 45678
                    )
                ),
                result
            )
        }

    @Test
    fun `GIVEN service returns empty list WHEN getVehicles is called THEN return empty list`() =
        runBlocking {
            // GIVEN
            val size = 0
            coEvery { service.getVehicles(size) } returns emptyList()

            // WHEN
            val result = repository.getVehicles(size)

            // THEN
            assertEquals(emptyList<Vehicle>(), result)
        }

    @Test(expected = IllegalStateException::class)
    fun `GIVEN service throws illegal state exception WHEN getVehicles is called THEN throw exception`() {
        runBlocking {
            // GIVEN
            val size = 1
            coEvery { service.getVehicles(size) } throws IllegalStateException()

            // WHEN
            repository.getVehicles(size)
        }
    }
}
