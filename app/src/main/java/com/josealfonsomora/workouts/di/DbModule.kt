package com.josealfonsomora.workouts.di

import android.content.Context
import android.database.sqlite.SQLiteDatabase
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
}