package com.example.physicalexercise.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.physicalexercise.data.models.Achievement
import com.example.physicalexercise.data.models.Challenge
import com.example.physicalexercise.data.repositories.AchievementRepository
import com.example.physicalexercise.data.repositories.ChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val achievementRepository: AchievementRepository,
    private val challengeRepository: ChallengeRepository
) : ViewModel() {

    private val _achievements = MutableStateFlow<List<Achievement>>(emptyList())
    val achievements: StateFlow<List<Achievement>> = _achievements.asStateFlow()

    private val _challenges = MutableStateFlow<List<Challenge>>(emptyList())
    val challenges: StateFlow<List<Challenge>> = _challenges.asStateFlow()

    private val _userLevel = MutableStateFlow(1)
    val userLevel: StateFlow<Int> = _userLevel.asStateFlow()

    private val _userXp = MutableStateFlow(0)
    val userXp: StateFlow<Int> = _userXp.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadAchievements()
        loadChallenges()
        loadUserProgress()
    }

    private fun loadAchievements() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _achievements.value = achievementRepository.getAllAchievements()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load achievements"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadChallenges() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _challenges.value = challengeRepository.getActiveChallenges()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load challenges"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadUserProgress() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // TODO: Implement user progress loading from repository
                _userLevel.value = 1
                _userXp.value = 0
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load user progress"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createChallenge(
        name: String,
        description: String,
        duration: Int,
        goal: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val challenge = Challenge(
                    name = name,
                    description = description,
                    duration = duration,
                    goal = goal
                )
                challengeRepository.createChallenge(challenge)
                loadChallenges()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to create challenge"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun joinChallenge(challenge: Challenge) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                challengeRepository.joinChallenge(challenge)
                loadChallenges()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to join challenge"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun leaveChallenge(challenge: Challenge) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                challengeRepository.leaveChallenge(challenge)
                loadChallenges()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to leave challenge"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun retry() {
        loadAchievements()
        loadChallenges()
        loadUserProgress()
    }
} 