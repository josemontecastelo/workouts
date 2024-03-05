package com.josealfonsomora.workouts.features.planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josealfonsomora.workouts.data.repositories.DailyWorkoutsRepository
import com.josealfonsomora.workouts.domain.Workout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlannerViewModel @Inject constructor(
    private val workoutsRepository: DailyWorkoutsRepository
) : ViewModel() {
    private val _state = MutableStateFlow<PlannerState>(PlannerState.Loading)
    val state = _state

    init {
        viewModelScope.launch {
            workoutsRepository.getWorkouts().collect {
                _state.value = PlannerState.Loaded(it)
            }
        }
        viewModelScope.launch {
            workoutsRepository.addRandomWorkouts()
            workoutsRepository.deleteAllWorkouts()
        }
    }
}

sealed class PlannerState {
    data object Loading : PlannerState()
    data class Loaded(val workouts: List<Workout>) : PlannerState()
}
