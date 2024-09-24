package com.justtwago.rides.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VehicleNavArgument(
    val vin: String,
    val makeAndModel: String,
    val color: String,
    val carType: String
) : Parcelable {
    companion object {
        val Default = VehicleNavArgument(
            vin = "",
            makeAndModel = "",
            color = "",
            carType = ""
        )
    }
}