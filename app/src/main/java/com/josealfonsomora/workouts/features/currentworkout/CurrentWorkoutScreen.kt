package com.josealfonsomora.workouts.features.currentworkout

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

@Composable
fun CurrentWorkoutScreen(
    id: Int,
){
    Text(text = "Current Workout Screen $id", style = TextStyle.Default)
}