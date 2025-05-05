package com.example.physicalexercise.data.local.dao

import androidx.room.*
import com.example.physicalexercise.data.local.entities.AchievementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements WHERE userId = :userId")
    fun getAchievementsForUser(userId: String): Flow<List<AchievementEntity>>

    @Query("SELECT * FROM achievements WHERE userId = :userId AND isCompleted = 1")
    fun getCompletedAchievementsForUser(userId: String): Flow<List<AchievementEntity>>

    @Query("SELECT * FROM achievements WHERE id = :achievementId")
    fun getAchievementById(achievementId: String): Flow<AchievementEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievement(achievement: AchievementEntity)

    @Update
    suspend fun updateAchievement(achievement: AchievementEntity)

    @Delete
    suspend fun deleteAchievement(achievement: AchievementEntity)

    @Query("SELECT * FROM achievements WHERE userId = :userId AND type = :type")
    fun getAchievementsByType(userId: String, type: String): Flow<List<AchievementEntity>>
} 