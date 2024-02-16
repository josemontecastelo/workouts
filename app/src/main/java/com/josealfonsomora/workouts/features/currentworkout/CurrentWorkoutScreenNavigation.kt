package com.josealfonsomora.workouts.features.currentworkout

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.josealfonsomora.workouts.features.planner.navigateToPlanner

const val CURRENT_WORKOUT_ROUTE = "current_workout"
const val CURRENT_WORKOUT_ID_KEY = "id"

fun NavHostController.navigateToCurrentWorkoutScreen(id: Int) {
    this.navigate("$CURRENT_WORKOUT_ROUTE/$id") // current_workout/5
}

fun NavGraphBuilder.currentWorkoutScreen() {
    composable(
        route = "$CURRENT_WORKOUT_ROUTE/{$CURRENT_WORKOUT_ID_KEY}", // current_workout/{id}
        arguments = listOf(
            navArgument(CURRENT_WORKOUT_ID_KEY) {
                type = androidx.navigation.NavType.IntType
            }
        )
    ) {
        it.arguments?.getInt(CURRENT_WORKOUT_ID_KEY)?.let { id ->
            CurrentWorkoutScreen(id)
        }
    }
}