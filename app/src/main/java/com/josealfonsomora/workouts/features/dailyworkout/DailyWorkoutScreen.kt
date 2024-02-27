package com.josealfonsomora.workouts.features.dailyworkout

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.josealfonsomora.workouts.domain.Muscle
import com.josealfonsomora.workouts.ui.theme.WorkoutsTheme

@Composable
fun DailyWorkoutScreen(
    modifier: Modifier = Modifier,
    viewModel: DailyWorkoutViewModel = hiltViewModel(),
    navigateToPlanner: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    when (state) {
        DailyWorkoutState.Loading -> {
            DailyWorkoutLoading()
        }

        is DailyWorkoutState.Loaded -> {
            DailyWorkoutContent(
                state = state as DailyWorkoutState.Loaded,
                navigateToPlanner = navigateToPlanner
            )
        }
    }
}

@Composable
fun DailyWorkoutContent(
    modifier: Modifier = Modifier,
    state: DailyWorkoutState.Loaded,
    navigateToPlanner: (Int) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier, columns = GridCells.Fixed(3)
    ) {
        itemsIndexed(state.workouts) { index, workout ->
            WorkoutCard(name = workout.name, image = workout.image, index = index) { id ->
                navigateToPlanner(id)
            }
        }
    }
}

@Composable
fun DailyWorkoutLoading() {
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
fun WorkoutCard(
    name: String, @DrawableRes image: Int, index: Int, navigateToPlanner: (Int) -> Unit
) {
    Box(modifier = Modifier.clickable { navigateToPlanner(index) }) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Workout image",
        )
        Text(text = name)
    }
}

@Preview
@Composable
fun DailyWorkoutLoadingPreview() {
    WorkoutsTheme {
        DailyWorkoutLoading()
    }
}

@Preview
@Composable
fun DailyWorkoutContentPreview() {
    WorkoutsTheme {
        DailyWorkoutContent(
            state = DailyWorkoutState.Loaded(
                listOf<WorkoutUI>()
            ),
            navigateToPlanner = {}
        )
    }
}

data class WorkoutUI(
    val name: String,
    @DrawableRes val image: Int,
    val muscles: List<Muscle> = emptyList(),
)

