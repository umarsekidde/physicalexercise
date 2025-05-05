package com.example.physicalexercise.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "challenge_participants",
    foreignKeys = [
        ForeignKey(
            entity = ChallengeEntity::class,
            parentColumns = ["id"],
            childColumns = ["challengeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("challengeId"), Index("userId")]
)
data class ChallengeParticipantEntity(
    @PrimaryKey
    val id: String,
    val challengeId: String,
    val userId: String,
    val progress: Int,
    val rank: Int
) 