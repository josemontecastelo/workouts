package com.josealfonsomora.workouts.data.sources.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WorkoutEntity::class], version = 1)
abstract class WorkoutRoomDB: RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
}