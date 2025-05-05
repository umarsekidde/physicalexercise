package com.example.physicalexercise.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.physicalexercise.data.models.Workout
import com.example.physicalexercise.data.models.WorkoutDifficulty
import com.example.physicalexercise.data.repositories.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutsViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> = _workouts.asStateFlow()

    private val _selectedDifficulty = MutableStateFlow<WorkoutDifficulty?>(null)
    val selectedDifficulty: StateFlow<WorkoutDifficulty?> = _selectedDifficulty.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadWorkouts()
    }

    private fun loadWorkouts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val filteredWorkouts = _selectedDifficulty.value?.let { difficulty ->
                    workoutRepository.getWorkoutsByDifficulty(difficulty)
                } ?: workoutRepository.getAllWorkouts()
                _workouts.value = filteredWorkouts
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load workouts"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setDifficulty(difficulty: WorkoutDifficulty?) {
        _selectedDifficulty.value = difficulty
        loadWorkouts()
    }

    fun addWorkout(
        name: String,
        description: String,
        difficulty: WorkoutDifficulty,
        duration: Int,
        caloriesBurned: Int
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val workout = Workout(
                    name = name,
                    description = description,
                    difficulty = difficulty,
                    duration = duration,
                    caloriesBurned = caloriesBurned
                )
                workoutRepository.insertWorkout(workout)
                loadWorkouts()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to add workout"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                workoutRepository.deleteWorkout(workout)
                loadWorkouts()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to delete workout"
            } finally {
                _isLoading.value = false
            }
        }
    }
} 