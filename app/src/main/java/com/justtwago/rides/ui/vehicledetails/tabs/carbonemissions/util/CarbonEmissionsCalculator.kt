package com.justtwago.rides.ui.vehicledetails.tabs.carbonemissions.util

import javax.inject.Inject

private const val INITIAL_THRESHOLD = 5000
private const val INITIAL_EMISSION_RATE = 1.0
private const val SUBSEQUENT_EMISSION_RATE = 1.5

class CarbonEmissionsCalculator @Inject constructor() {

    fun calculateEmissions(kilometrage: Int): Double = when {
        kilometrage <= INITIAL_THRESHOLD -> kilometrage * INITIAL_EMISSION_RATE
        else -> {
            val initialEmissions = INITIAL_THRESHOLD * INITIAL_EMISSION_RATE
            val subsequentKilometrage = kilometrage - INITIAL_THRESHOLD
            val subsequentEmissions = subsequentKilometrage * SUBSEQUENT_EMISSION_RATE
            initialEmissions + subsequentEmissions
        }
    }
}
