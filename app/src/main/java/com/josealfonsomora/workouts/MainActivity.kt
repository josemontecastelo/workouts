@file:OptIn(ExperimentalMaterial3Api::class)

package com.josealfonsomora.workouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.josealfonsomora.workouts.ui.theme.WorkoutsTheme
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.DateRange
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutsTheme {
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        WorkoutsTopAppBar(scrollBehavior = scrollBehavior)
                    },
                    bottomBar = {
                        WorkoutsBottomAppBar()
                    },
                ) { paddingValues ->
                    WorkoutScreen(modifier = Modifier.padding(paddingValues))
                }
            }
        }
    }

    @Composable
    private fun WorkoutScreen(
        modifier: Modifier = Modifier
    ) {
        val workouts = listOf(
            Workout("Kettlebell", R.drawable.kettlebell),
            Workout("TRX", R.drawable.trx),
            Workout("Cardio", R.drawable.cardio),
            Workout("TRX", R.drawable.trx),
            Workout("Kettlebell", R.drawable.kettlebell),
            Workout("TRX", R.drawable.trx),
            Workout("Kettlebell", R.drawable.kettlebell),
            Workout("TRX", R.drawable.trx),
            Workout("Cardio", R.drawable.cardio),
            Workout("TRX", R.drawable.trx),
            Workout("Kettlebell", R.drawable.kettlebell),
            Workout("TRX", R.drawable.trx),
            Workout("Kettlebell", R.drawable.kettlebell),
            Workout("TRX", R.drawable.trx),
            Workout("Cardio", R.drawable.cardio),
            Workout("TRX", R.drawable.trx),
            Workout("Kettlebell", R.drawable.kettlebell),
            Workout("TRX", R.drawable.trx),
            Workout("Kettlebell", R.drawable.kettlebell),
            Workout("TRX", R.drawable.trx),
            Workout("Cardio", R.drawable.cardio),
            Workout("TRX", R.drawable.trx),
            Workout("Kettlebell", R.drawable.kettlebell),
            Workout("TRX", R.drawable.trx),
        )
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(3)){
            items(workouts) { workout ->
                WorkoutCard(name = workout.name, image = workout.image)
            }
        }
    }
}


data class Workout(val name:String, @DrawableRes val image: Int)

@Composable
fun WorkoutCard(name: String, @DrawableRes image: Int) {
    Box{
        Image(
            painter = painterResource(id = image),
            contentDescription = "Workout image",
        )
        Text(text = name)
    }
}

@Composable
fun WorkoutsTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
) {
//    TopAppBar(modifier = Modifier, title = { Text(text = "Workouts") })
    LargeTopAppBar(
        title = { Text(text = "Workouts") },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.TwoTone.ArrowBack, contentDescription = "Workouts")
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.TwoTone.DateRange, contentDescription = "Custom")
            }
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.TwoTone.Settings, contentDescription = "Settings")
            }
        },
        scrollBehavior = scrollBehavior
    )
//    MediumTopAppBar(
//        title = { Text(text = "Workouts") }, navigationIcon = {
//        IconButton(onClick = {}) {
//            Icon(imageVector = Icons.TwoTone.ArrowBack, contentDescription = "Workouts")
//        }
//    }, actions = {
//        IconButton(onClick = {}) {
//            Icon(imageVector = Icons.TwoTone.DateRange, contentDescription = "Custom")
//        }
//        IconButton(onClick = {}) {
//            Icon(imageVector = Icons.TwoTone.Settings, contentDescription = "Settings")
//        }
//    },
//        scrollBehavior = scrollBehavior
//    )
}

@Composable
fun WorkoutsBottomAppBar(
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier,
        actions = {
        IconButton(onClick = {}) {
            Icon(imageVector = Icons.TwoTone.Home, contentDescription = "Workouts")
        }
        IconButton(onClick = {}) {

            Icon(imageVector = Icons.TwoTone.DateRange, contentDescription = "Custom")
        }
        IconButton(onClick = {}) {
            Icon(imageVector = Icons.TwoTone.Settings, contentDescription = "Settings")
        }
    })
}