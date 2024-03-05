package com.josealfonsomora.workouts.features.planner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.josealfonsomora.workouts.domain.Workout
import com.josealfonsomora.workouts.features.dailyworkout.DailyWorkoutContent
import com.josealfonsomora.workouts.features.dailyworkout.DailyWorkoutLoading
import com.josealfonsomora.workouts.features.dailyworkout.DailyWorkoutState
import com.josealfonsomora.workouts.features.dailyworkout.WorkoutCard
import com.josealfonsomora.workouts.ui.theme.WorkoutsTheme

@Composable
fun PlannerScreen(
    viewModel: PlannerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    when (state) {
        PlannerState.Loading -> {
            DailyWorkoutLoading()
        }

        is PlannerState.Loaded -> {
            PlannerContent(workouts = (state as PlannerState.Loaded).workouts)
        }
    }
}

@Composable
fun PlannerLoading() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Loading..")
            Spacer(modifier = Modifier.padding(16.dp))
            CircularProgressIndicator()
        }
    }
}

@Composable
fun PlannerContent(
    modifier: Modifier = Modifier,
    workouts: List<Workout>
){
    val context = LocalContext.current

    LazyVerticalGrid(
        modifier = modifier, columns = GridCells.Fixed(3)
    ) {
        itemsIndexed(workouts) { index, workout ->
            val id = context.resources.getIdentifier(workout.image, "drawable", context.packageName)
            WorkoutCard(name = workout.name, image = id, index = index) { }
        }
    }
}

@Preview
@Composable
fun PlannerScreenPreview() {
    WorkoutsTheme {
        PlannerScreen()
    }
}