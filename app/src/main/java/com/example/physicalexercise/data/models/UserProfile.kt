package com.example.physicalexercise.data.models

import java.util.Date

data class UserProfile(
    val id: String = "",
    val name: String = "",
    val age: Int = 0,
    val weight: Float = 0f,
    val height: Float = 0f,
    val gender: String = "",
    val goal: FitnessGoal = FitnessGoal.WEIGHT_LOSS,
    val dietaryPreference: DietaryPreference = DietaryPreference.REGULAR,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class FitnessGoal {
    WEIGHT_LOSS,
    MUSCLE_GAIN,
    ENDURANCE,
    MAINTENANCE
}

enum class DietaryPreference {
    REGULAR,
    VEGAN,
    VEGETARIAN,
    KETO,
    PALEO
} 