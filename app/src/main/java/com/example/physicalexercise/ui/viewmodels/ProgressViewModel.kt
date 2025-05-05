package com.example.physicalexercise.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.physicalexercise.data.models.Mood
import com.example.physicalexercise.data.models.Progress
import com.example.physicalexercise.data.repositories.ProgressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val progressRepository: ProgressRepository
) : ViewModel() {

    private val _todayProgress = MutableStateFlow<Progress?>(null)
    val todayProgress: StateFlow<Progress?> = _todayProgress.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadTodayProgress()
    }

    private fun loadTodayProgress() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _todayProgress.value = progressRepository.getProgressByDate(LocalDate.now())
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load progress"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProgress(
        weight: Float,
        steps: Int,
        waterIntake: Float,
        sleepHours: Float,
        mood: Mood,
        energyLevel: Int,
        workoutMinutes: Int,
        caloriesBurned: Int
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val progress = Progress(
                    date = LocalDate.now(),
                    weight = weight,
                    steps = steps,
                    waterIntake = waterIntake,
                    sleepHours = sleepHours,
                    mood = mood,
                    energyLevel = energyLevel,
                    workoutMinutes = workoutMinutes,
                    caloriesBurned = caloriesBurned
                )
                progressRepository.insertProgress(progress)
                _todayProgress.value = progress
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to update progress"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getProgressHistory(days: Int = 7): StateFlow<List<Progress>> {
        val _progressHistory = MutableStateFlow<List<Progress>>(emptyList())
        viewModelScope.launch {
            try {
                val history = progressRepository.getRecentProgress(days)
                _progressHistory.value = history
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load progress history"
            }
        }
        return _progressHistory.asStateFlow()
    }
} 