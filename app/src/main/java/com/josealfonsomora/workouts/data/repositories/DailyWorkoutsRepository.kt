package com.josealfonsomora.workouts.data.repositories

import com.josealfonsomora.workouts.domain.Muscle
import com.josealfonsomora.workouts.domain.Workout
import com.josealfonsomora.workouts.domain.WorkoutType
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DailyWorkoutsRepository @Inject constructor() {
    suspend fun getDailyWorkouts(): List<Workout> {
        return withContext(IO) {
            delay(1000)
            listOf(
                Workout(
                    name = "Becky Coffey",
                    workoutType = WorkoutType.CARDIO,
                    description = "turpis",
                    muscles = listOf(Muscle.BACK, Muscle.ABS)
                ), Workout(
                    name = "Hilda Perry",
                    workoutType = WorkoutType.TRX,
                    description = "habitant",
                    muscles = listOf(Muscle.CALVES)
                ), Workout(
                    name = "Priscilla Ruiz",
                    workoutType = WorkoutType.DUMBBELL,
                    description = "eu",
                    muscles = listOf()
                ), Workout(
                    name = "Sergio Johns",
                    workoutType = WorkoutType.STRENGTH,
                    description = "reprimique",
                    muscles = listOf()
                )
            )
        }
    }
}