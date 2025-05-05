package com.example.physicalexercise.data.local.dao

import androidx.room.*
import com.example.physicalexercise.data.local.entities.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises WHERE workoutId = :workoutId")
    fun getExercisesForWorkout(workoutId: String): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE id = :exerciseId")
    fun getExerciseById(exerciseId: String): Flow<ExerciseEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: ExerciseEntity)

    @Update
    suspend fun updateExercise(exercise: ExerciseEntity)

    @Delete
    suspend fun deleteExercise(exercise: ExerciseEntity)

    @Query("SELECT * FROM exercises WHERE muscleGroup = :muscleGroup")
    fun getExercisesByMuscleGroup(muscleGroup: String): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE difficulty = :difficulty")
    fun getExercisesByDifficulty(difficulty: String): Flow<List<ExerciseEntity>>
} 