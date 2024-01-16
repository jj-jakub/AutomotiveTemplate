package com.example.automotivetemplate.src

import android.content.Intent
import androidx.car.app.Screen
import androidx.car.app.Session
import androidx.lifecycle.DefaultLifecycleObserver

class TemplateAppSession : Session(), DefaultLifecycleObserver {
    override fun onCreateScreen(intent: Intent): Screen {
        lifecycle.addObserver(this)
        return TemplateAppScreen(carContext)
    }
}