package com.example.automotivetemplate.src.presentation.carappscreen

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.net.Uri
import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.Action
import androidx.car.app.model.CarIcon
import androidx.car.app.model.ItemList
import androidx.car.app.model.ListTemplate
import androidx.car.app.model.Row
import androidx.car.app.model.Template
import androidx.core.graphics.drawable.IconCompat
import androidx.lifecycle.lifecycleScope
import com.example.automotivetemplate.R
import com.example.automotivetemplate.src.presentation.carappscreen.model.MenuItem
import com.example.automotivetemplate.src.presentation.secondscreen.SecondActivity
import com.example.automotivetemplate.src.presentation.settings.SettingsScreen
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CarAppScreen(carContext: CarContext) : Screen(carContext), KoinComponent {

    private val carAppScreenViewModel by inject<CarAppScreenViewModel>()

    private val menuItems = listOf(
        MenuItem(
            name = "Settings",
            icon = getDefaultItemIcon(),
            action = ::goToSettingsScreen,
        ),
        MenuItem(
            name = "Second Activity",
            icon = CarIcon.Builder(
                IconCompat.createWithResource(
                    carContext,
                    R.drawable.ic_launcher_background,
                )
            ).build(),
            action = ::startSecondActivity,
        )
    )

    private fun getDefaultItemIcon(): CarIcon = CarIcon.Builder(
        IconCompat.createWithResource(
            carContext,
            R.drawable.ic_launcher_foreground,
        )
    ).build()

    init {
        observeState()
        requestPermissions()
    }

    private fun callAssistance() {
        val manufacturerAssistancePhone =
            carAppScreenViewModel.viewState.value.manufacturerAssistancePhone
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$manufacturerAssistancePhone")
            flags = FLAG_ACTIVITY_NEW_TASK
        }
        carContext.startActivity(intent)
    }

    private fun goToSettingsScreen() {
        screenManager.push(SettingsScreen(carContext = carContext))
    }

    private fun startSecondActivity() {
        carContext.startActivity(Intent(carContext, SecondActivity::class.java).apply {
            flags = FLAG_ACTIVITY_NEW_TASK
        })
    }

    override fun onGetTemplate(): Template {
        val state = carAppScreenViewModel.viewState.value
        val vehicleDataItem = Row.Builder()
            .setTitle("Speed: ${state.speed}; Gear: ${state.gear}; ignitionState: ${state.ignitionState}\n brakeState: ${state.brakeState}; fuelLevelLow?: ${state.fuelState}")
            .setImage(getDefaultItemIcon())
            .build()
        val callAssistanceItem = Row.Builder()
            .setTitle("Call ${state.manufacturer} Assistance ${state.manufacturerAssistancePhone}")
            .setImage(
                CarIcon.Builder(
                    IconCompat.createWithResource(
                        carContext,
                        R.drawable.ic_assistance,
                    )
                ).build()
            )
            .setOnClickListener { callAssistance() }
            .build()

        val itemList = ItemList.Builder()
        itemList.addItem(callAssistanceItem)
        menuItems.forEach { menuData ->
            itemList.addItem(
                Row.Builder()
                    .setTitle(menuData.name)
                    .setImage(menuData.icon)
                    .setOnClickListener { menuData.action() }
                    .build()
            )
        }
        itemList.addItem(vehicleDataItem)

        return ListTemplate.Builder()
            .setTitle(carContext.getString(R.string.roadside_assistance) + "; Manufacturer: " + state.manufacturer + "; Model: " + state.model)
            .setSingleList(itemList.build())
            .setHeaderAction(Action.APP_ICON)
            .build()
    }

    private fun observeState() {
        lifecycleScope.launch {
            carAppScreenViewModel.viewState.collect {
                invalidate()
            }
        }
    }

    private fun requestPermissions() {
        runCatching {
            val requiredPermissions = carAppScreenViewModel.getRequiredPermissions()
            if (requiredPermissions.any { carContext.checkSelfPermission(it) == PERMISSION_DENIED }) {
                carContext.requestPermissions(requiredPermissions) { grantedPermissions, rejectedPermissions ->
                    if (grantedPermissions.isNotEmpty()) {
                        carAppScreenViewModel.onPermissionsGranted()
                    }
                }
            }
        }
    }
}