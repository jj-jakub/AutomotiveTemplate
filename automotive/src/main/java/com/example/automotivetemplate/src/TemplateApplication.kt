package com.example.automotivetemplate.src

import android.app.Application
import com.example.automotivetemplate.src.di.startKoin

class TemplateApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this)
    }
}