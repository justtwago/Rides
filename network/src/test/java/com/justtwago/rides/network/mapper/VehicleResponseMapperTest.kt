package com.justtwago.rides.network.mapper

import com.justtwago.rides.domain.model.Vehicle
import com.justtwago.rides.network.model.VehicleResponse
import org.junit.Before
import org.junit.Test
import junit.framework.TestCase.assertEquals

internal class VehicleResponseMapperTest {
    private lateinit var mapper: VehicleResponseMapper

    @Before
    fun setup() {
        mapper = VehicleResponseMapper()
    }

    @Test
    fun `GIVEN valid VehicleResponse WHEN mapToVehicleDomain is called THEN return correctly mapped Vehicle`() {
        // GIVEN
        val vehicleResponse = VehicleResponse(
            vin = "1HGCM82633A004352",
            makeAndModel = "Honda Civic",
            color = "Blue",
            carType = "Sedan"
        )

        val expected = Vehicle(
            vin = "1HGCM82633A004352",
            makeAndModel = "Honda Civic",
            color = "Blue",
            carType = "Sedan"
        )

        // WHEN
        val result = mapper.mapToVehicleDomain(vehicleResponse)

        // THEN
        assertEquals(expected, result)
    }
}