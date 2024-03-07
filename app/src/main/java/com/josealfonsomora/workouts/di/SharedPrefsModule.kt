package com.josealfonsomora.workouts.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkoutsSharedPreferences

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SettingsSharedPreferences


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefsModule {

    @WorkoutsSharedPreferences
    @Provides
    fun providesWorkoutsSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("workouts", Context.MODE_PRIVATE)

    @SettingsSharedPreferences
    @Provides
    fun providesSettingsSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    @Provides
    fun providesSettingsDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore
}