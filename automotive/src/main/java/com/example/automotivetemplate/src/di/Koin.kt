package com.example.automotivetemplate.src.di

import android.content.Context
import com.example.automotivetemplate.src.data.DefaultCarInfoRepository
import com.example.automotivetemplate.src.domain.CarInfoRepository
import com.example.automotivetemplate.src.domain.usecases.GetManufacturerAssistancePhone
import com.example.automotivetemplate.src.presentation.carappscreen.CarAppScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


fun startKoin(applicationContext: Context) {
    startKoin {
        androidContext(applicationContext)
        modules(coreModule)
    }
}

private val coreModule = module {
    single<CarInfoRepository> { DefaultCarInfoRepository(androidContext()) }
    single { GetManufacturerAssistancePhone() }

    viewModel<CarAppScreenViewModel> {
        CarAppScreenViewModel(
            carInfoRepository = get(),
            getManufacturerAssistancePhone = get(),
        )
    }
}