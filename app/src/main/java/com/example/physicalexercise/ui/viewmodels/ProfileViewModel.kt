package com.example.physicalexercise.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.physicalexercise.data.models.DietaryPreference
import com.example.physicalexercise.data.models.FitnessGoal
import com.example.physicalexercise.data.models.UserProfile
import com.example.physicalexercise.data.repositories.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile: StateFlow<UserProfile?> = _profile.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _profile.value = userProfileRepository.getUserProfile()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load profile"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProfile(
        name: String,
        age: Int,
        weight: Float,
        height: Float,
        gender: String,
        fitnessGoal: FitnessGoal,
        dietaryPreference: DietaryPreference
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val updatedProfile = UserProfile(
                    name = name,
                    age = age,
                    weight = weight,
                    height = height,
                    gender = gender,
                    fitnessGoal = fitnessGoal,
                    dietaryPreference = dietaryPreference
                )
                userProfileRepository.updateUserProfile(updatedProfile)
                _profile.value = updatedProfile
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to update profile"
            } finally {
                _isLoading.value = false
            }
        }
    }
} 