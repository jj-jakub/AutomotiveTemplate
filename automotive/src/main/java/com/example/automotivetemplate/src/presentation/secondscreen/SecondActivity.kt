package com.example.automotivetemplate.src.presentation.secondscreen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private val viewModel: SecondActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getBrand()

        // Possibly Compose views can be created here for Automotive apps
    }
}