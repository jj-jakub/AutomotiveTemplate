package com.example.automotivetemplate.src.presentation.carappscreen

import android.car.VehicleGear
import android.car.VehicleIgnitionState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.automotivetemplate.src.domain.AppInfoRepository
import com.example.automotivetemplate.src.domain.CarInfoRepository
import com.example.automotivetemplate.src.domain.usecases.DEFAULT_ASSISTANCE_PHONE
import com.example.automotivetemplate.src.domain.usecases.GetManufacturerAssistancePhone
import com.example.automotivetemplate.src.presentation.carappscreen.model.CarAppScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.round

class CarAppScreenViewModel(
    private val appInfoRepository: AppInfoRepository,
    private val carInfoRepository: CarInfoRepository,
    private val getManufacturerAssistancePhone: GetManufacturerAssistancePhone,
) : ViewModel() {

    private val _viewState = MutableStateFlow(
        CarAppScreenState(
            manufacturer = "",
            manufacturerAssistancePhone = DEFAULT_ASSISTANCE_PHONE,
            model = "",
            speed = "",
            gear = "",
            ignitionState = "",
            brakeState = "",
            fuelState = "",
        )
    )

    val viewState = _viewState.asStateFlow()

    private var observeVehicleManufacturerJob: Job? = null
    private var observeVehicleModelJob: Job? = null
    private var observeVehicleSpeedJob: Job? = null
    private var observeVehicleGearJob: Job? = null
    private var observeVehicleIgnitionStateJob: Job? = null
    private var observeVehicleParkingBrakeStateJob: Job? = null
    private var observeVehicleFuelStateJob: Job? = null

    init {
        collectData()
    }

    fun onPermissionsGranted() {
        collectData()
    }

    fun getRequiredPermissions() = appInfoRepository.getRequiredPermissions()

    private fun collectData() {
        collectVehicleManufacturerState()
        collectVehicleModelState()
        collectVehicleSpeed()
        collectVehicleGear()
        collectVehicleIgnitionState()
        collectVehicleParkingBrakeState()
        collectVehicleFuelLevelLowState()
    }

    private fun collectVehicleManufacturerState() {
        observeVehicleManufacturerJob?.cancel()
        observeVehicleManufacturerJob = viewModelScope.launch {
            carInfoRepository.observeVehicleManufacturer().collect { value ->
                val manufacturer = value?.value.toString()
                _viewState.value = viewState.value.copy(
                    manufacturer = manufacturer,
                    manufacturerAssistancePhone = getManufacturerAssistancePhone(manufacturer),
                )
            }
        }
    }

    private fun collectVehicleModelState() {
        observeVehicleModelJob?.cancel()
        observeVehicleModelJob = viewModelScope.launch {
            carInfoRepository.observeVehicleModel().collect { value ->
                _viewState.value = viewState.value.copy(
                    model = value?.value.toString(),
                )
            }
        }
    }

    private fun collectVehicleSpeed() {
        observeVehicleSpeedJob?.cancel()
        observeVehicleSpeedJob = viewModelScope.launch {
            carInfoRepository.observeVehicleSpeed().collect { value ->
                val speedMS = value?.value.toString().toDoubleOrNull()
                _viewState.value = viewState.value.copy(
                    speed = ((metersPerSecondToMPH(value = speedMS) ?: "-").toString() + " MPH"),
                )
            }
        }
    }

    private fun collectVehicleGear() {
        observeVehicleGearJob?.cancel()
        observeVehicleGearJob = viewModelScope.launch {
            carInfoRepository.observeVehicleGear().collect { value ->
                _viewState.value =
                    viewState.value.copy(
                        gear = value?.value.toString().toIntOrNull()
                            ?.let { VehicleGear.toString(it) }.toString()
                    )
            }
        }
    }

    private fun collectVehicleIgnitionState() {
        observeVehicleIgnitionStateJob?.cancel()
        observeVehicleIgnitionStateJob = viewModelScope.launch {
            carInfoRepository.observeVehicleIgnitionState().collect { value ->
                _viewState.value = viewState.value.copy(
                    ignitionState = value?.value?.toString()?.toIntOrNull()
                        ?.let { VehicleIgnitionState.toString(it) } ?: "",
                )
            }
        }
    }

    private fun collectVehicleParkingBrakeState() {
        observeVehicleParkingBrakeStateJob?.cancel()
        observeVehicleParkingBrakeStateJob = viewModelScope.launch {
            carInfoRepository.observeVehicleParkingBrakeState().collect { value ->
                _viewState.value = viewState.value.copy(
                    brakeState = value?.value.toString(),
                )
            }
        }
    }

    private fun collectVehicleFuelLevelLowState() {
        observeVehicleFuelStateJob?.cancel()
        observeVehicleFuelStateJob = viewModelScope.launch {
            carInfoRepository.observeVehicleFuelLevelLowlState().collect { value ->
                _viewState.value = viewState.value.copy(
                    fuelState = value?.value.toString(),
                )
            }
        }
    }

    private fun metersPerSecondToMPH(value: Double?): Double? {
        if (value == null) return null
        return round((value * 2.23694) * 100) / 100
    }
}