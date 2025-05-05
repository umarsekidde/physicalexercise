package com.example.physicalexercise.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.physicalexercise.data.models.BodyMeasurements
import com.example.physicalexercise.data.models.Mood
import java.util.Date

@Entity(
    tableName = "progress",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class ProgressEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val date: Date,
    val weight: Float,
    val bmi: Float,
    val bodyMeasurements: BodyMeasurements,
    val steps: Int,
    val caloriesBurned: Int,
    val waterIntake: Float,
    val sleepHours: Float,
    val mood: Mood,
    val energyLevel: Int
) 