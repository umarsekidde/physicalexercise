package com.example.physicalexercise.data.local.dao

import androidx.room.*
import com.example.physicalexercise.data.local.entities.ProgressEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ProgressDao {
    @Query("SELECT * FROM progress WHERE userId = :userId ORDER BY date DESC")
    fun getProgressForUser(userId: String): Flow<List<ProgressEntity>>

    @Query("SELECT * FROM progress WHERE userId = :userId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getProgressForUserInDateRange(userId: String, startDate: Date, endDate: Date): Flow<List<ProgressEntity>>

    @Query("SELECT * FROM progress WHERE id = :progressId")
    fun getProgressById(progressId: String): Flow<ProgressEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: ProgressEntity)

    @Update
    suspend fun updateProgress(progress: ProgressEntity)

    @Delete
    suspend fun deleteProgress(progress: ProgressEntity)

    @Query("SELECT * FROM progress WHERE userId = :userId ORDER BY date DESC LIMIT 1")
    fun getLatestProgress(userId: String): Flow<ProgressEntity?>
} 