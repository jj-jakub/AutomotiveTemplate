package com.example.automotivetemplate.src

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.ItemList
import androidx.car.app.model.ListTemplate
import androidx.car.app.model.Row
import androidx.car.app.model.Template

class SecondTemplateAppScreen(carContext: CarContext) : Screen(carContext) {
    override fun onGetTemplate(): Template {
        val row = Row.Builder()
            .setTitle("SecondAutomotiveTemplateScreen")
            .setOnClickListener {
                screenManager.push(TemplateAppScreen(carContext = carContext))
            }.build()

        val list = ItemList.Builder().addItem(row).build()

        return ListTemplate.Builder()
            .setSingleList(list)
            .setTitle("AutomotiveTemplateList")
            .build()
    }
}