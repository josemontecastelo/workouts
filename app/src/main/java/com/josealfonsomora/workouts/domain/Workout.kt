package com.josealfonsomora.workouts.domain

import com.josealfonsomora.workouts.R
import com.josealfonsomora.workouts.features.dailyworkout.WorkoutUI

data class Workout(
    val name: String,
    val workoutType: WorkoutType,
    val description: String,
    val muscles: List<Muscle>,
    val image: String = "cardio",
)

fun Workout.toWorkoutUI(): WorkoutUI {
    return WorkoutUI(
        name = name,
        image = when (workoutType) {
            WorkoutType.CARDIO -> R.drawable.cardio
            WorkoutType.STRENGTH -> R.drawable.kettlebell
            WorkoutType.FLEXIBILITY -> R.drawable.cardio
            WorkoutType.KETTLEBELL -> R.drawable.kettlebell
            WorkoutType.TRX -> R.drawable.trx
            WorkoutType.DUMBBELL -> R.drawable.kettlebell
        },
        muscles = muscles,
    )
}

enum class WorkoutType {
    CARDIO, STRENGTH, FLEXIBILITY, KETTLEBELL, TRX, DUMBBELL,
}

enum class Muscle {
    CHEST, BACK, SHOULDERS, BICEPS, TRICEPS, QUADS, HAMSTRINGS, GLUTES, CALVES, ABS,
}