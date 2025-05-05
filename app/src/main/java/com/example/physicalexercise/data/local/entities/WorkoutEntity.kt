package com.example.physicalexercise.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.physicalexercise.data.models.WorkoutDifficulty

@Entity(
    tableName = "workouts",
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
data class WorkoutEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val difficulty: WorkoutDifficulty,
    val duration: Int,
    val isCustom: Boolean,
    val userId: String
) 