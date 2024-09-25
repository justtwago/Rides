package com.justtwago.rides.ui.vehicledetails.tabs.carbonemissions.util

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class CarbonEmissionsCalculatorTest {

    private lateinit var calculator: CarbonEmissionsCalculator

    @Before
    fun setup() {
        calculator = CarbonEmissionsCalculator()
    }

    @Test
    fun `GIVEN kilometrage of 0 WHEN calculateEmissions is called THEN return 0`() {
        // GIVEN
        val kilometrage = 0

        // WHEN
        val result = calculator.calculateEmissions(kilometrage)

        // THEN
        assertEquals(0.0, result)
    }

    @Test
    fun `GIVEN kilometrage of 5000 WHEN calculateEmissions is called THEN return 5000`() {
        // GIVEN
        val kilometrage = 5000

        // WHEN
        val result = calculator.calculateEmissions(kilometrage)

        // THEN
        assertEquals(5000.0, result)
    }

    @Test
    fun `GIVEN kilometrage of 5001 WHEN calculateEmissions is called THEN return 5001_5`() {
        // GIVEN
        val kilometrage = 5001

        // WHEN
        val result = calculator.calculateEmissions(kilometrage)

        // THEN
        assertEquals(5001.5, result)
    }

    @Test
    fun `GIVEN kilometrage of 10000 WHEN calculateEmissions is called THEN return 12500`() {
        // GIVEN
        val kilometrage = 10000

        // WHEN
        val result = calculator.calculateEmissions(kilometrage)

        // THEN
        assertEquals(12500.0, result)
    }
}