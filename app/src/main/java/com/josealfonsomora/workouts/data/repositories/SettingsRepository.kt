package com.josealfonsomora.workouts.data.repositories

import android.content.SharedPreferences
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.josealfonsomora.workouts.di.SettingsSharedPreferences
import com.josealfonsomora.workouts.features.settings.DayOfWeek
import com.josealfonsomora.workouts.features.settings.DayOfWeek.DOMINGO
import com.josealfonsomora.workouts.features.settings.DayOfWeek.JUEVES
import com.josealfonsomora.workouts.features.settings.DayOfWeek.LUNES
import com.josealfonsomora.workouts.features.settings.DayOfWeek.MARTES
import com.josealfonsomora.workouts.features.settings.DayOfWeek.MIERCOLES
import com.josealfonsomora.workouts.features.settings.DayOfWeek.SABADO
import com.josealfonsomora.workouts.features.settings.DayOfWeek.VIERNES
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    @SettingsSharedPreferences private val sharedPrefs: SharedPreferences,
    private val settingsDataStore: DataStore<Preferences>,
) {
    fun saveSettings(day: String, isChecked: Boolean) {
        Log.d("SettingsRepository", "Saving $day with value $isChecked")
        sharedPrefs.edit().putBoolean(day, isChecked).apply()
        runBlocking {
            settingsDataStore.edit { preferences ->
                preferences[booleanPreferencesKey(day)] = isChecked
            }
        }
    }

    fun getSettings(day: String): Boolean {
        Log.d("SettingsRepository", "Getting $day")
        return sharedPrefs.getBoolean(day, false)
    }

    suspend fun getSettingsDataStore(): Flow<Map<DayOfWeek, Boolean>> =
        settingsDataStore.data.map { preferences: Preferences ->
            mapOf<DayOfWeek, Boolean>(
                LUNES to (preferences[booleanPreferencesKey(LUNES.name)] ?: false),
                MARTES to (preferences[booleanPreferencesKey(MARTES.name)] ?: false),
                MIERCOLES to (preferences[booleanPreferencesKey(MIERCOLES.name)] ?: false),
                JUEVES to (preferences[booleanPreferencesKey(JUEVES.name)] ?: false),
                VIERNES to (preferences[booleanPreferencesKey(VIERNES.name)] ?: false),
                SABADO to (preferences[booleanPreferencesKey(SABADO.name)] ?: false),
                DOMINGO to (preferences[booleanPreferencesKey(DOMINGO.name)] ?: false),
            )
        }
}