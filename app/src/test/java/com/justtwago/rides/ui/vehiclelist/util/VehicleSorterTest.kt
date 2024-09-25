package com.justtwago.rides.ui.vehiclelist.util

import com.justtwago.rides.domain.model.Vehicle
import com.justtwago.rides.ui.vehiclelist.model.VehicleSorting
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class VehicleSorterTest {
    private lateinit var sorter: VehiclesSorter

    @Before
    fun setup() {
        sorter = VehiclesSorter()
    }

    @Test
    fun `GIVEN a list of vehicles and VIN sorting WHEN sortVehicles is called THEN return the list sorted by VIN`() {
        // GIVEN
        val vehicle1 = Vehicle(
            vin = "123",
            makeAndModel = "Toyota Camry",
            color = "Red",
            carType = "Sedan",
            kilometrage = 45678
        )
        val vehicle2 = Vehicle(
            vin = "456",
            makeAndModel = "Honda Civic",
            color = "Blue",
            carType = "Hatchback",
            kilometrage = 45678
        )
        val vehicle3 = Vehicle(
            vin = "789",
            makeAndModel = "Ford Mustang",
            color = "Black",
            carType = "Coupe",
            kilometrage = 45678
        )
        val vehicles = listOf(vehicle2, vehicle1, vehicle3)

        // WHEN
        val sortedVehicles = sorter.sortVehicles(vehicles, VehicleSorting.VIN)

        // THEN
        assertEquals(listOf(vehicle1, vehicle2, vehicle3), sortedVehicles)
    }

    @Test
    fun `GIVEN a list of vehicles and CAR_TYPE sorting WHEN sortVehicles is called THEN return the list sorted by car type`() {
        // GIVEN
        val vehicle1 = Vehicle(
            vin = "123",
            makeAndModel = "Toyota Camry",
            color = "Red",
            carType = "Sedan",
            kilometrage = 45678
        )
        val vehicle2 = Vehicle(
            vin = "456",
            makeAndModel = "Honda Civic",
            color = "Blue",
            carType = "Hatchback",
            kilometrage = 45678
        )
        val vehicle3 = Vehicle(
            vin = "789",
            makeAndModel = "Ford Mustang",
            color = "Black",
            carType = "Coupe",
            kilometrage = 45678
        )
        val vehicles = listOf(vehicle1, vehicle3, vehicle2)

        // WHEN
        val sortedVehicles = sorter.sortVehicles(vehicles, VehicleSorting.CAR_TYPE)

        // THEN
        assertEquals(listOf(vehicle3, vehicle2, vehicle1), sortedVehicles)
    }
}