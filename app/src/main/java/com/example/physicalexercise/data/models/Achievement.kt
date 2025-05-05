package com.example.physicalexercise.data.models

import java.util.Date

data class Achievement(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val description: String = "",
    val type: AchievementType = AchievementType.STEPS,
    val target: Int = 0,
    val progress: Int = 0,
    val isCompleted: Boolean = false,
    val completedAt: Date? = null,
    val icon: String = "" // Local resource path for the achievement icon
)

data class Challenge(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val type: ChallengeType = ChallengeType.STEPS,
    val startDate: Date = Date(),
    val endDate: Date = Date(),
    val target: Int = 0,
    val participants: List<String> = emptyList(), // List of user IDs
    val leaderboard: List<ChallengeParticipant> = emptyList()
)

data class ChallengeParticipant(
    val userId: String = "",
    val progress: Int = 0,
    val rank: Int = 0
)

enum class AchievementType {
    STEPS,
    WORKOUTS,
    WEIGHT_LOSS,
    STREAK,
    WATER_INTAKE,
    SLEEP,
    CUSTOM
}

enum class ChallengeType {
    STEPS,
    WORKOUTS,
    WEIGHT_LOSS,
    WATER_INTAKE,
    SLEEP,
    CUSTOM
} 