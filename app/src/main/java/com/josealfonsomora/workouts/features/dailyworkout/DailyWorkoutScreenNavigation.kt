package com.josealfonsomora.workouts.features.dailyworkout

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.josealfonsomora.workouts.features.currentworkout.navigateToCurrentWorkoutScreen
import com.josealfonsomora.workouts.features.planner.navigateToPlanner

const val DAILY_WORKOUT_ROUTE = "daily_workout"

fun NavHostController.navigateToDailyWorkout() {
    this.navigate(DAILY_WORKOUT_ROUTE)
}

fun NavGraphBuilder.dailyWorkoutScreen(
    navHostController: NavHostController
) {
    composable(route = DAILY_WORKOUT_ROUTE) {
        DailyWorkoutScreen(navigateToPlanner = { id ->
            navHostController.navigateToCurrentWorkoutScreen(id)
        })
    }
}