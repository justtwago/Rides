package com.justtwago.rides.domain.repository

import com.justtwago.rides.domain.model.Vehicle

interface VehiclesRepository {
    suspend fun getVehicles(size: Int): List<Vehicle>
}