package com.example.automotivetemplate.src.domain

import android.car.hardware.CarPropertyValue
import kotlinx.coroutines.flow.Flow

interface CarInfoRepository {
    suspend fun observeVehicleManufacturer(): Flow<CarPropertyValue<*>?>
    suspend fun observeVehicleModel(): Flow<CarPropertyValue<*>?>
    suspend fun observeVehicleSpeed(): Flow<CarPropertyValue<*>?>
    suspend fun observeVehicleGear(): Flow<CarPropertyValue<*>?>
    suspend fun observeVehicleIgnitionState(): Flow<CarPropertyValue<*>?>
    suspend fun observeVehicleParkingBrakeState(): Flow<CarPropertyValue<*>?>
    suspend fun observeVehicleFuelLevelLowlState(): Flow<CarPropertyValue<*>?>
}