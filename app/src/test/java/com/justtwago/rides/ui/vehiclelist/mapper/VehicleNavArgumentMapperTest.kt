package com.justtwago.rides.ui.vehiclelist.mapper

import com.justtwago.rides.domain.model.Vehicle
import com.justtwago.rides.ui.mapper.VehicleNavArgumentMapper
import com.justtwago.rides.ui.model.VehicleNavArgument
import org.junit.Assert.assertEquals
import org.junit.Test

internal class VehicleNavArgumentMapperTest {
    private val mapper = VehicleNavArgumentMapper()

    @Test
    fun `GIVEN a vehicle WHEN mapToVehicleNavArgument is called THEN return correctly mapped VehicleNavArgument`() {
        // GIVEN
        val vehicle = Vehicle(
            vin = "123",
            makeAndModel = "Toyota Camry",
            color = "Red",
            carType = "Sedan",
            kilometrage = 45678
        )

        // WHEN
        val navArgument = mapper.mapToVehicleNavArgument(vehicle)

        // THEN
        assertEquals(
            VehicleNavArgument(
                vin = "123",
                makeAndModel = "Toyota Camry",
                color = "Red",
                carType = "Sedan",
                kilometrage = 45678
            ),
            navArgument
        )
    }

    @Test
    fun `GIVEN a vehicle nav argument WHEN mapToVehicleDomain is called THEN return correctly mapped Vehicle`() {
        // GIVEN
        val vehicleNavArgument = VehicleNavArgument(
            vin = "123",
            makeAndModel = "Toyota Camry",
            color = "Red",
            carType = "Sedan",
            kilometrage = 45678
        )

        // WHEN
        val vehicle = mapper.mapToVehicleDomain(vehicleNavArgument)

        // THEN
        assertEquals(
            Vehicle(
                vin = "123",
                makeAndModel = "Toyota Camry",
                color = "Red",
                carType = "Sedan",
                kilometrage = 45678
            ),
            vehicle
        )
    }
}
