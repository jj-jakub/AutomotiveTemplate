package com.example.automotivetemplate.src.domain.usecases

const val DEFAULT_ASSISTANCE_PHONE = "555555555"

class GetManufacturerAssistancePhone {
    operator fun invoke(manufacturer: String) = when (manufacturer) {
        "Peterbilt" -> "1-800-473-8372"
        "Kenworth" -> "1-800-592-7747"
        "Paclease" -> "1-800-759-2979"
        else -> "444444444"
    }
}