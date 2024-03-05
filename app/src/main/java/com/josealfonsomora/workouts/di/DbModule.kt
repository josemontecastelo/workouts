package com.josealfonsomora.workouts.di

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import com.josealfonsomora.workouts.data.sources.room.WorkoutDao
import com.josealfonsomora.workouts.data.sources.room.WorkoutRoomDB
import com.josealfonsomora.workouts.data.sources.sqlite.WorkoutsDbHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    fun providesSQLiteWorkoutsDB(@ApplicationContext context: Context): SQLiteDatabase {
        return WorkoutsDbHelper(context).readableDatabase
    }

    @Provides
    fun providesWorkoutsRoomDb(@ApplicationContext context: Context): WorkoutRoomDB =
        Room.databaseBuilder(
            context, WorkoutRoomDB::class.java, "workouts_room"
        ).build()

    @Provides
    fun providesWorkoutDao(workoutRoomDB: WorkoutRoomDB): WorkoutDao = workoutRoomDB.workoutDao()

}