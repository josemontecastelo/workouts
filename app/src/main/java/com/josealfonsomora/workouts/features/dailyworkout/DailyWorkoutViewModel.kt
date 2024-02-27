package com.josealfonsomora.workouts.features.dailyworkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josealfonsomora.workouts.data.repositories.DailyWorkoutsRepository
import com.josealfonsomora.workouts.domain.toWorkoutUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyWorkoutViewModel @Inject constructor(
    private val dailyWorkoutsRepository: DailyWorkoutsRepository
) : ViewModel() {

    private var _state = MutableStateFlow<DailyWorkoutState>(DailyWorkoutState.Loading)
    val state: StateFlow<DailyWorkoutState> = _state

    init {
        viewModelScope.launch {
            _state.value = DailyWorkoutState.Loaded(
                workouts = dailyWorkoutsRepository.getDailyWorkouts()
                    .map { workout -> workout.toWorkoutUI() })
        }
    }
}

sealed interface DailyWorkoutState {
    data object Loading : DailyWorkoutState
    data class Loaded(val workouts: List<WorkoutUI>) : DailyWorkoutState
}