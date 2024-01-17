package com.example.automotivetemplate.src.presentation.carappscreen.model

data class CarAppScreenState(
    val manufacturer: String,
    val manufacturerAssistancePhone: String,
    val model: String,
    val speed: String,
    val gear: String,
    val ignitionState: String,
    val brakeState: String,
    val fuelState: String,
)
