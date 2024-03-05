package com.josealfonsomora.workouts.data.sources.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workout")
    fun getAllWorkouts(): Flow<List<WorkoutEntity>>

    @Insert
    fun insertWorkout(vararg workout: WorkoutEntity)

    @Query("DELETE FROM workout")
    fun deleteAll()

    @Delete
    fun deleteWorkout(workout: WorkoutEntity)
}