package com.example.automotivetemplate.src.presentation.secondscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.automotivetemplate.src.domain.CarInfoRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SecondActivityViewModel : ViewModel(), KoinComponent {

    private val carInfoRepository: CarInfoRepository by inject()

    fun getBrand() = viewModelScope.launch {
        carInfoRepository.observeVehicleSpeed()
    }
}