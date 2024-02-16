package com.josealfonsomora.workouts.features.planner

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

const val PLANNER_ROUTE = "workout_planner"

fun NavHostController.navigateToPlanner(){
    this.navigate(PLANNER_ROUTE)
}

fun NavGraphBuilder.plannerScreen() {
    composable(route = PLANNER_ROUTE) {
        PlannerScreen()
    }
}