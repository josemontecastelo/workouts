package com.josealfonsomora.workouts.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josealfonsomora.workouts.data.repositories.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
): ViewModel() {

    private val _state = MutableStateFlow<SettingsState>(SettingsState.Loading)
    val state: StateFlow<SettingsState> = _state

    init {
//        _state.value = SettingsState.Loaded(
//            lunes = settingsRepository.getSettings(DayOfWeek.LUNES.name),
//            martes = settingsRepository.getSettings(DayOfWeek.MARTES.name),
//            miercoles = settingsRepository.getSettings(DayOfWeek.MIERCOLES.name),
//            jueves = settingsRepository.getSettings(DayOfWeek.JUEVES.name),
//            viernes = settingsRepository.getSettings(DayOfWeek.VIERNES.name),
//            sabado = settingsRepository.getSettings(DayOfWeek.SABADO.name),
//            domingo = settingsRepository.getSettings(DayOfWeek.DOMINGO.name),
//        )
        viewModelScope.launch {
            settingsRepository.getSettingsDataStore().collect {
                _state.value = SettingsState.Loaded(
                    lunes = it[DayOfWeek.LUNES]!!,
                    martes = it[DayOfWeek.MARTES]!!,
                    miercoles = it[DayOfWeek.MIERCOLES]!!,
                    jueves = it[DayOfWeek.JUEVES]!!,
                    viernes = it[DayOfWeek.VIERNES]!!,
                    sabado = it[DayOfWeek.SABADO]!!,
                    domingo = it[DayOfWeek.DOMINGO]!!,
                )
            }
        }
    }

    fun onCheckedChange(day: DayOfWeek) {
        val currentState = _state.value
        if (currentState is SettingsState.Loaded) {
            when (day) {
//            _state.value = when (day) {
                DayOfWeek.LUNES -> {
                    settingsRepository.saveSettings(day.name, !currentState.lunes)
//                    currentState.copy(lunes = !currentState.lunes)
                }
                DayOfWeek.MARTES -> {
                    settingsRepository.saveSettings(day.name, !currentState.martes)
//                    currentState.copy(martes = !currentState.martes)
                }
                DayOfWeek.MIERCOLES -> {
                    settingsRepository.saveSettings(day.name, !currentState.miercoles)
//                    currentState.copy(miercoles = !currentState.miercoles)
                }
                DayOfWeek.JUEVES -> {
                    settingsRepository.saveSettings(day.name, !currentState.jueves)
//                    currentState.copy(jueves = !currentState.jueves)
                }
                DayOfWeek.VIERNES -> {
                    settingsRepository.saveSettings(day.name, !currentState.viernes)
//                    currentState.copy(viernes = !currentState.viernes)
                }
                DayOfWeek.SABADO -> {
                    settingsRepository.saveSettings(day.name, !currentState.sabado)
//                    currentState.copy(sabado = !currentState.sabado)
                }
                DayOfWeek.DOMINGO -> {
                    settingsRepository.saveSettings(day.name, !currentState.domingo)
//                    currentState.copy(domingo = !currentState.domingo)
                }
            }
        }
    }
}

sealed interface SettingsState {
    data object Loading : SettingsState
    data class Loaded(
        val lunes: Boolean,
        val martes: Boolean,
        val miercoles: Boolean,
        val jueves: Boolean,
        val viernes: Boolean,
        val sabado: Boolean,
        val domingo: Boolean,
    ) : SettingsState
}

enum class DayOfWeek {
    LUNES,
    MARTES,
    MIERCOLES,
    JUEVES,
    VIERNES,
    SABADO,
    DOMINGO
}
