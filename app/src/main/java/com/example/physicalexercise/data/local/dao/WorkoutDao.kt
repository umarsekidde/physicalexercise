package com.example.physicalexercise.data.local.dao

import androidx.room.*
import com.example.physicalexercise.data.local.entities.WorkoutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts WHERE userId = :userId")
    fun getWorkoutsForUser(userId: String): Flow<List<WorkoutEntity>>

    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    fun getWorkoutById(workoutId: String): Flow<WorkoutEntity?>

    @Query("SELECT * FROM workouts WHERE isCustom = 0")
    fun getPreBuiltWorkouts(): Flow<List<WorkoutEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: WorkoutEntity)

    @Update
    suspend fun updateWorkout(workout: WorkoutEntity)

    @Delete
    suspend fun deleteWorkout(workout: WorkoutEntity)

    @Query("SELECT * FROM workouts WHERE difficulty = :difficulty AND isCustom = 0")
    fun getWorkoutsByDifficulty(difficulty: String): Flow<List<WorkoutEntity>>
} 