package com.example.physicalexercise.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.physicalexercise.data.models.DietaryPreference
import com.example.physicalexercise.data.models.FitnessGoal
import java.util.Date

@Entity(tableName = "user_profiles")
data class UserProfileEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val age: Int,
    val weight: Float,
    val height: Float,
    val gender: String,
    val goal: FitnessGoal,
    val dietaryPreference: DietaryPreference,
    val createdAt: Date,
    val updatedAt: Date
) 