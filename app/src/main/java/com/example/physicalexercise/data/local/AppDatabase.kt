package com.example.physicalexercise.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.physicalexercise.data.local.dao.*
import com.example.physicalexercise.data.local.entities.*

@Database(
    entities = [
        UserProfileEntity::class,
        ProgressEntity::class,
        WorkoutEntity::class,
        ExerciseEntity::class,
        FoodEntryEntity::class,
        FoodEntity::class,
        MealPlanEntity::class,
        MealEntity::class,
        AchievementEntity::class,
        ChallengeEntity::class,
        ChallengeParticipantEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun progressDao(): ProgressDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun foodEntryDao(): FoodEntryDao
    abstract fun foodDao(): FoodDao
    abstract fun mealPlanDao(): MealPlanDao
    abstract fun mealDao(): MealDao
    abstract fun achievementDao(): AchievementDao
    abstract fun challengeDao(): ChallengeDao
    abstract fun challengeParticipantDao(): ChallengeParticipantDao
} 