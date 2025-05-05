package com.example.physicalexercise

import android.app.Application
import androidx.room.Room
import com.example.physicalexercise.data.local.AppDatabase
import com.example.physicalexercise.data.repository.*

class PhysicalExerciseApp : Application() {
    private lateinit var database: AppDatabase

    val userProfileRepository: UserProfileRepository by lazy {
        UserProfileRepository(database.userProfileDao())
    }

    val progressRepository: ProgressRepository by lazy {
        ProgressRepository(database.progressDao())
    }

    val workoutRepository: WorkoutRepository by lazy {
        WorkoutRepository(database.workoutDao(), database.exerciseDao())
    }

    val foodRepository: FoodRepository by lazy {
        FoodRepository(database.foodDao(), database.foodEntryDao())
    }

    val achievementRepository: AchievementRepository by lazy {
        AchievementRepository(
            database.achievementDao(),
            database.challengeDao(),
            database.challengeParticipantDao()
        )
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "physical_exercise_db"
 