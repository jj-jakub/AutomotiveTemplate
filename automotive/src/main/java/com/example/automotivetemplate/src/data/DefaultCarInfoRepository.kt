package com.example.automotivetemplate.src.data

import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.content.Context
import com.example.automotivetemplate.src.domain.CarInfoRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

private const val DEFAULT_PROPERTY_UPDATE_RATE_HZ = 1F

class DefaultCarInfoRepository(
    context: Context,
) : CarInfoRepository {

    private val car: Car = Car.createCar(context)
    private val carPropertyManager = car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager

    private fun createCarPropertyEventCallback(send: (CarPropertyValue<*>?) -> Unit) =
        object : CarPropertyManager.CarPropertyEventCallback {
            override fun onChangeEvent(value: CarPropertyValue<*>?) {
                send(value)
            }

            override fun onErrorEvent(p0: Int, p1: Int) {}
        }

    override suspend fun observeVehicleModel() =
        getObservingFlow(VehiclePropertyIds.INFO_MODEL)

    override suspend fun observeVehicleManufacturer() =
        getObservingFlow(VehiclePropertyIds.INFO_MAKE)

    override suspend fun observeVehicleSpeed() =
        getObservingFlow(VehiclePropertyIds.PERF_VEHICLE_SPEED)

    override suspend fun observeVehicleGear() =
        getObservingFlow(VehiclePropertyIds.GEAR_SELECTION)

    override suspend fun observeVehicleIgnitionState() =
        getObservingFlow(VehiclePropertyIds.IGNITION_STATE)

    override suspend fun observeVehicleParkingBrakeState() =
        getObservingFlow(VehiclePropertyIds.PARKING_BRAKE_ON)

    override suspend fun observeVehicleFuelLevelLowlState() =
        getObservingFlow(VehiclePropertyIds.FUEL_LEVEL_LOW)

    private fun getObservingFlow(propertyId: Int) = callbackFlow {
        val callback = createCarPropertyEventCallback { value ->
            trySend(value)
        }
        carPropertyManager.registerCallback(
            /* carPropertyEventCallback = */ callback,
            /* propertyId = */ propertyId,
            /* updateRateHz = */ DEFAULT_PROPERTY_UPDATE_RATE_HZ,
        )
        awaitClose {
            carPropertyManager.unregisterCallback(callback)
            close()
        }
    }
}