@file:OptIn(ExperimentalMaterial3Api::class)

package com.josealfonsomora.workouts

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.josealfonsomora.workouts.data.sources.room.WorkoutRoomDB
import com.josealfonsomora.workouts.features.currentworkout.currentWorkoutScreen
import com.josealfonsomora.workouts.features.dailyworkout.DAILY_WORKOUT_ROUTE
import com.josealfonsomora.workouts.features.dailyworkout.dailyWorkoutScreen
import com.josealfonsomora.workouts.features.planner.PLANNER_ROUTE
import com.josealfonsomora.workouts.features.planner.plannerScreen
import com.josealfonsomora.workouts.features.settings.SETTINGS_ROUTE
import com.josealfonsomora.workouts.features.settings.settingsScreen
import com.josealfonsomora.workouts.ui.theme.WorkoutsTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sliteDB: SQLiteDatabase

    @Inject
    lateinit var roomDB: WorkoutRoomDB

    override fun onDestroy() {
        super.onDestroy()
        sliteDB.close()
        roomDB.close()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutsTheme {
                val scrollBehavior =
                    TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
                val navHostController = rememberNavController()
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        WorkoutsTopAppBar(scrollBehavior = scrollBehavior,
                            onBackClicked = { navHostController.popBackStack() })
                    },
                    bottomBar = {
                        WorkoutNavigationBar(navController = navHostController)
                    },
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        NavHost(
                            navController = navHostController,
                            startDestination = DAILY_WORKOUT_ROUTE
                        ) {
                            dailyWorkoutScreen(navHostController = navHostController)
                            plannerScreen()
                            settingsScreen()
                            currentWorkoutScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WorkoutsTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    onBackClicked: () -> Unit,
) {
//    TopAppBar(modifier = Modifier, title = { Text(text = "Workouts") })
    LargeTopAppBar(title = { Text(text = "Workouts") }, navigationIcon = {
        IconButton(onClick = {
            onBackClicked()
        }) {
            Icon(imageVector = Icons.TwoTone.ArrowBack, contentDescription = "Workouts")
        }
    }, scrollBehavior = scrollBehavior
    )
}

@Composable
fun WorkoutsBottomAppBar(
    modifier: Modifier = Modifier,
    navigateToPlanner: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    BottomAppBar(modifier = modifier, actions = {
        IconButton(onClick = {}) {
            Icon(imageVector = Icons.TwoTone.Home, contentDescription = "Workouts")
        }
        IconButton(onClick = {
            navigateToPlanner()
        }) {

            Icon(imageVector = Icons.TwoTone.DateRange, contentDescription = "Custom")
        }
        IconButton(onClick = {
            navigateToSettings()
        }) {
            Icon(imageVector = Icons.TwoTone.Settings, contentDescription = "Settings")
        }
    })
}

@Composable
fun WorkoutBottomNavigation(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    BottomNavigation {
        BottomNavigationScreens.entries.forEach {
            BottomNavigationItem(label = { Text(text = stringResource(id = it.label)) },
                selected = navBackStackEntry?.destination?.hierarchy?.any { entry -> entry.route == it.route } == true,
                onClick = {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = { Icon(imageVector = it.icon, contentDescription = "Settings") })
        }
    }
}

@Composable
fun WorkoutNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    NavigationBar {
        BottomNavigationScreens.entries.forEach {
            val selected =
                navBackStackEntry?.destination?.hierarchy?.any { entry -> entry.route == it.route } == true
            BottomNavigationItem(label = { Text(text = stringResource(id = it.label)) },
                selectedContentColor = Color.Red,
                selected = selected,
                onClick = {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = "Settings",
                        tint = if (selected) Color.Red else Color.Black
                    )
                })
        }
    }
}

enum class BottomNavigationScreens(
    val route: String,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    WORKOUTS(DAILY_WORKOUT_ROUTE, Icons.TwoTone.Home, R.string.daily_workout_title), PLANNER(
        PLANNER_ROUTE,
        Icons.TwoTone.DateRange,
        R.string.planner_title
    ),
    SETTINGS(SETTINGS_ROUTE, Icons.TwoTone.Settings, R.string.settings_title)
}
