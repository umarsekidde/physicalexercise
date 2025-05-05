package com.example.physicalexercise.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class DashboardViewModel @Inject constructor(
    private val progressRepository: ProgressRepository
) : ViewModel() {

    private val _todayStats = MutableStateFlow<TodayStats>(TodayStats())
    val todayStats: StateFlow<TodayStats> = _todayStats.asStateFlow()

    private val _recentActivity = MutableStateFlow<List<Progress>>(emptyList())
    val recentActivity: StateFlow<List<Progress>> = _recentActivity.asStateFlow()

    init {
        loadTodayStats()
        loadRecentActivity()
    }

    private fun loadTodayStats() {
        viewModelScope.launch {
            try {
                val todayProgress = progressRepository.getProgressByDate(LocalDate.now())
                _todayStats.value = TodayStats(
                    steps = todayProgress?.steps ?: 0,
                    workoutMinutes = todayProgress?.workoutMinutes ?: 0,
                    caloriesBurned = todayProgress?.caloriesBurned ?: 0
                )
            } catch (e: Exception) {
                // TODO: Handle error state
            }
        }
    }

    private fun loadRecentActivity() {
        viewModelScope.launch {
            try {
                val recentProgress = progressRepository.getRecentProgress(7) // Last 7 days
                _recentActivity.value = recentProgress
            } catch (e: Exception) {
                // TODO: Handle error state
            }
        }
    }

    fun refresh() {
        loadTodayStats()
        loadRecentActivity()
    }
}

data class TodayStats(
    val steps: Int = 0,
    val workoutMinutes: Int = 0,
    val caloriesBurned: Int = 0
) 