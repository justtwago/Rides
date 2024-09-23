package com.justtwago.rides.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class VehicleResponse(
    @Json(name = "vin") val vin: String,
    @Json(name = "make_and_model") val makeAndModel: String,
    @Json(name = "color") val color: String,
    @Json(name = "car_type") val carType: String
)
