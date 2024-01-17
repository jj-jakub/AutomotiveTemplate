package com.example.automotivetemplate.src.presentation.carappscreen.model

import androidx.car.app.model.CarIcon

data class MenuItem(
    val name: String,
    val icon: CarIcon,
    val action: () -> Unit,
)
