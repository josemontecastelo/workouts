package com.josealfonsomora.workouts.features.dailyworkout

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.josealfonsomora.workouts.data.workouts

@Composable
fun DailyWorkoutScreen(
    modifier: Modifier = Modifier,
    navigateToPlanner: (Int) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier, columns = GridCells.Fixed(3)
    ) {
        itemsIndexed(workouts) { index, workout ->
            WorkoutCard(name = workout.name, image = workout.image, index = index) { id ->
                navigateToPlanner(id)
            }
        }
    }
}

@Composable
fun WorkoutCard(
    name: String,
    @DrawableRes image: Int,
    index: Int,
    navigateToPlanner: (Int) -> Unit
) {
    Box(modifier = Modifier.clickable { navigateToPlanner(index) }) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Workout image",
        )
        Text(text = name)
    }
}

data class Workout(val name: String, @DrawableRes val image: Int)

