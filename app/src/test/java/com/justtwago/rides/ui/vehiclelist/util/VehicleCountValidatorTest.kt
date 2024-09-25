package com.justtwago.rides.ui.vehiclelist.util

import com.justtwago.rides.ui.vehiclelist.util.VehicleCountValidator.VehicleCountValidationResult.Invalid
import com.justtwago.rides.ui.vehiclelist.util.VehicleCountValidator.VehicleCountValidationResult.Valid
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class VehicleCountValidatorTest {
    private lateinit var validator: VehicleCountValidator

    @Before
    fun setup() {
        validator = VehicleCountValidator()
    }

    @Test
    fun `GIVEN empty string WHEN isQueryValid is called THEN return Empty`() {
        // GIVEN
        val input = ""

        // WHEN
        val result = validator.isQueryValid(input)

        // THEN
        assertEquals(Invalid.Empty, result)
    }

    @Test
    fun `GIVEN blank string WHEN isQueryValid is called THEN return Empty`() {
        // GIVEN
        val input = "   "

        // WHEN
        val result = validator.isQueryValid(input)

        // THEN
        assertEquals(Invalid.Empty, result)
    }

    @Test
    fun `GIVEN string with value 0 WHEN isQueryValid is called THEN return LessThanOne`() {
        // GIVEN
        val input = "0"

        // WHEN
        val result = validator.isQueryValid(input)

        // THEN
        assertEquals(Invalid.LessThanOne, result)
    }

    @Test
    fun `GIVEN string with negative value WHEN isQueryValid is called THEN return LessThanOne`() {
        // GIVEN
        val input = "-5"

        // WHEN
        val result = validator.isQueryValid(input)

        // THEN
        assertEquals(Invalid.LessThanOne, result)
    }

    @Test
    fun `GIVEN string with value 1 WHEN isQueryValid is called THEN return Valid with count 1`() {
        // GIVEN
        val input = "1"

        // WHEN
        val result = validator.isQueryValid(input)

        // THEN
        assertEquals(Valid(1), result)
    }

    @Test
    fun `GIVEN string with value 50 WHEN isQueryValid is called THEN return Valid with count 50`() {
        // GIVEN
        val input = "50"

        // WHEN
        val result = validator.isQueryValid(input)

        // THEN
        assertEquals(Valid(50), result)
    }

    @Test
    fun `GIVEN string with value 100 WHEN isQueryValid is called THEN return Valid with count 100`() {
        // GIVEN
        val input = "100"

        // WHEN
        val result = validator.isQueryValid(input)

        // THEN
        assertEquals(Valid(100), result)
    }

    @Test
    fun `GIVEN string with value 101 WHEN isQueryValid is called THEN return GreaterThanHundred`() {
        // GIVEN
        val input = "101"

        // WHEN
        val result = validator.isQueryValid(input)

        // THEN
        assertEquals(
            Invalid.GreaterThanHundred,
            result
        )
    }

    @Test
    fun `GIVEN string with non-numeric value WHEN isQueryValid is called THEN return NotANumber`() {
        // GIVEN
        val input = "abc"

        // WHEN
        val result = validator.isQueryValid(input)

        // THEN
        assertEquals(Invalid.NotANumber, result)
    }

    @Test
    fun `GIVEN string with decimal value WHEN isQueryValid is called THEN return NotANumber`() {
        // GIVEN
        val input = "1.5"

        // WHEN
        val result = validator.isQueryValid(input)

        // THEN
        assertEquals(Invalid.NotANumber, result)
    }
}