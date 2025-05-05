package com.example.physicalexercise.data.models

data class Workout(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val difficulty: WorkoutDifficulty = WorkoutDifficulty.BEGINNER,
    val duration: Int = 0, // in minutes
    val exercises: List<Exercise> = emptyList(),
    val isCustom: Boolean = false,
    val userId: String = "" // for custom workouts
)

data class Exercise(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val muscleGroup: MuscleGroup = MuscleGroup.FULL_BODY,
    val difficulty: ExerciseDifficulty = ExerciseDifficulty.BEGINNER,
    val instructions: List<String> = emptyList(),
    val gifUrl: String = "", // Local resource path for the exercise GIF
    val sets: Int = 0,
    val reps: Int = 0,
    val duration: Int = 0 // in seconds, for timed exercises
)

enum class WorkoutDifficulty {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED
}

enum class ExerciseDifficulty {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED
}

enum class MuscleGroup {
    CHEST,
    BACK,
    SHOULDERS,
    BICEPS,
    TRICEPS,
    LEGS,
    CORE,
    FULL_BODY,
    CARDIO
} 