package com.example.physicalexercise.data.local.dao

import androidx.room.*
import com.example.physicalexercise.data.local.entities.ChallengeParticipantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChallengeParticipantDao {
    @Query("SELECT * FROM challenge_participants WHERE challengeId = :challengeId ORDER BY rank ASC")
    fun getParticipantsForChallenge(challengeId: String): Flow<List<ChallengeParticipantEntity>>

    @Query("SELECT * FROM challenge_participants WHERE userId = :userId")
    fun getChallengesForUser(userId: String): Flow<List<ChallengeParticipantEntity>>

    @Query("SELECT * FROM challenge_participants WHERE id = :participantId")
    fun getParticipantById(participantId: String): Flow<ChallengeParticipantEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParticipant(participant: ChallengeParticipantEntity)

    @Update
    suspend fun updateParticipant(participant: ChallengeParticipantEntity)

    @Delete
    suspend fun deleteParticipant(participant: ChallengeParticipantEntity)

    @Query("SELECT * FROM challenge_participants WHERE challengeId = :challengeId AND userId = :userId")
    fun getParticipantForChallenge(challengeId: String, userId: String): Flow<ChallengeParticipantEntity?>
} 