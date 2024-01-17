package com.example.automotivetemplate.src.data

import android.Manifest
import android.car.Car
import com.example.automotivetemplate.src.domain.AppInfoRepository

class DefaultAppInfoRepository : AppInfoRepository {
    override fun getRequiredPermissions(): List<String> = listOf(
        Car.PERMISSION_SPEED,
        Car.PERMISSION_CAR_INFO,
        Car.PERMISSION_ENERGY,
        Car.PERMISSION_READ_DISPLAY_UNITS,
        Car.PERMISSION_ENERGY_PORTS,
        Car.PERMISSION_POWERTRAIN,
        Manifest.permission.CALL_PHONE,
    )
}