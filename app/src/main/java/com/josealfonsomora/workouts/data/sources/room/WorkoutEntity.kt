package com.josealfonsomora.workouts.data.sources.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.josealfonsomora.workouts.domain.Muscle
import com.josealfonsomora.workouts.domain.Workout
import com.josealfonsomora.workouts.domain.WorkoutType

@Entity(tableName = "workout")
@TypeConverters(Converters::class)
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    @ColumnInfo("workout_type") val workoutType: WorkoutType,
    val description: String,
    val muscles: List<Muscle>,
    val image: String,
    val date: String,
    val done: Boolean
)

fun WorkoutEntity.toWorkout() = Workout(
    name = name,
    workoutType = workoutType,
    description = description,
    muscles = muscles,
    image = image,

)