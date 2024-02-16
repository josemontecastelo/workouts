package com.josealfonsomora.workouts.features.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

const val SETTINGS_ROUTE = "settings"

fun NavHostController.navigateToSettings(){
    this.navigate(SETTINGS_ROUTE)
}

fun NavGraphBuilder.settingsScreen() {
    composable(route = SETTINGS_ROUTE) {
        SettingsScreen()
    }
}