package com.justtwago.rides.domain.model

data class Vehicle(
    val vin: String,
    val makeAndModel: String,
    val color: String,
    val carType: String,
    val kilometrage: Int
)