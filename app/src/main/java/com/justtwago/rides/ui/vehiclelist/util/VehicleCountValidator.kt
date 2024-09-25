package com.justtwago.rides.ui.vehiclelist.util

import javax.inject.Inject

class VehicleCountValidator @Inject constructor() {

    fun isQueryValid(query: String): VehicleCountValidationResult {
        return try {
            if (query.isBlank()) return VehicleCountValidationResult.Invalid.Empty

            val count = query.toInt()
            when {
                count in 1..100 -> VehicleCountValidationResult.Valid(count)
                count < 1 -> VehicleCountValidationResult.Invalid.LessThanOne
                else -> VehicleCountValidationResult.Invalid.GreaterThanHundred
            }
        } catch (e: NumberFormatException) {
            VehicleCountValidationResult.Invalid.NotANumber
        }
    }

    sealed interface VehicleCountValidationResult {
        data class Valid(val count: Int) : VehicleCountValidationResult
        sealed interface Invalid : VehicleCountValidationResult {
            data object Empty : Invalid
            data object LessThanOne : Invalid
            data object GreaterThanHundred : Invalid
            data object NotANumber : Invalid
        }
    }
}
