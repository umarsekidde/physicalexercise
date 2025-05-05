package com.example.physicalexercise.data.models

import java.util.Date

data class Progress(
    val id: String = "",
    val userId: String = "",
    val date: Date = Date(),
    val weight: Float = 0f,
    val bmi: Float = 0f,
    val bodyMeasurements: BodyMeasurements = BodyMeasurements(),
    val steps: Int = 0,
    val caloriesBurned: Int = 0,
    val waterIntake: Float = 0f,
    val sleepHours: Float = 0f,
    val mood: Mood = Mood.NEUTRAL,
    val energyLevel: Int = 5 // 1-10 scale
)

data class BodyMeasurements(
    val chest: Float = 0f,
    val waist: Float = 0f,
    val hips: Float = 0f,
    val biceps: Float = 0f,
    val thighs: Float = 0f
)

enum class Mood {
    VERY_HAPPY,
    HAPPY,
    NEUTRAL,
    SAD,
    VERY_SAD
} 