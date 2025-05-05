package com.example.physicalexercise.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.physicalexercise.data.models.ExerciseDifficulty
import com.example.physicalexercise.data.models.MuscleGroup

@Entity(
    tableName = "exercises",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("workoutId")]
)
data class ExerciseEntity(
    @PrimaryKey
    val id: String,
    val workoutId: String,
    val name: String,
    val description: String,
    val muscleGroup: MuscleGroup,
    val difficulty: ExerciseDifficulty,
    val instructions: List<String>,
    val gifUrl: String,
    val sets: Int,
    val reps: Int,
    val duration: Int
) 