package com.example.automotivetemplate.src

import androidx.car.app.CarAppService
import androidx.car.app.Session
import androidx.car.app.validation.HostValidator

class TemplateAppService : CarAppService() {
    override fun createHostValidator(): HostValidator =
        HostValidator.Builder(applicationContext).build()

    override fun onCreateSession(): Session {
        return TemplateAppSession()
    }
}