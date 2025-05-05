package com.example.physicalexercise.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.physicalexercise.data.models.AchievementType
import java.util.Date

@Entity(
    tableName = "achievements",
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
data class AchievementEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val name: String,
    val description: String,
    val type: AchievementType,
    val target: Int,
    val progress: Int,
    val isCompleted: Boolean,
    val completedAt: Date?,
    val icon: String
) 