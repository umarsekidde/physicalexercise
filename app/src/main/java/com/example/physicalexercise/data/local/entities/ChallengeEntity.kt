package com.example.physicalexercise.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.physicalexercise.data.models.ChallengeType
import java.util.Date

@Entity(tableName = "challenges")
data class ChallengeEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val type: ChallengeType,
    val startDate: Date,
    val endDate: Date,
    val target: Int
) 