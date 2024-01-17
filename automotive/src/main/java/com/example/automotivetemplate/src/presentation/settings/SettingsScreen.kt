package com.example.automotivetemplate.src.presentation.settings

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.ItemList
import androidx.car.app.model.ListTemplate
import androidx.car.app.model.Row
import androidx.car.app.model.Template
import org.koin.core.component.KoinComponent

class SettingsScreen(carContext: CarContext) : Screen(carContext), KoinComponent {
    override fun onGetTemplate(): Template {
        val row = Row.Builder()
            .setTitle("Settings")
            .build()

        val list = ItemList.Builder().addItem(row).build()

        return ListTemplate.Builder()
            .setSingleList(list)
            .setTitle("Settings")
            .build()
    }
}