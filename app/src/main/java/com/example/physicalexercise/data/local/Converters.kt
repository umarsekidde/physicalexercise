package com.example.physicalexercise.data.local

import androidx.room.TypeConverter
import com.example.physicalexercise.data.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromFitnessGoal(value: FitnessGoal): String {
        return value.name
    }

    @TypeConverter
    fun toFitnessGoal(value: String): FitnessGoal {
        return FitnessGoal.valueOf(value)
    }

    @TypeConverter
    fun fromDietaryPreference(value: DietaryPreference): String {
        return value.name
    }

    @TypeConverter
    fun toDietaryPreference(value: String): DietaryPreference {
        return DietaryPreference.valueOf(value)
    }

    @TypeConverter
    fun fromBodyMeasurements(value: BodyMeasurements): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toBodyMeasurements(value: String): BodyMeasurements {
        return gson.fromJson(value, BodyMeasurements::class.java)
    }

    @TypeConverter
    fun fromMood(value: Mood): String {
        return value.name
    }

    @TypeConverter
    fun toMood(value: String): Mood {
        return Mood.valueOf(value)
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromWorkoutDifficulty(value: WorkoutDifficulty): String {
        return value.name
    }

    @TypeConverter
    fun toWorkoutDifficulty(value: String): WorkoutDifficulty {
        return WorkoutDifficulty.valueOf(value)
    }

    @TypeConverter
    fun fromExerciseDifficulty(value: ExerciseDifficulty): String {
        return value.name
    }

    @TypeConverter
    fun toExerciseDifficulty(value: String): ExerciseDifficulty {
        return ExerciseDifficulty.valueOf(value)
    }

    @TypeConverter
    fun fromMuscleGroup(value: MuscleGroup): String {
        return value.name
    }

    @TypeConverter
    fun toMuscleGroup(value: String): MuscleGroup {
        return MuscleGroup.valueOf(value)
    }

    @TypeConverter
    fun fromMealType(value: MealType): String {
        return value.name
    }

    @TypeConverter
    fun toMealType(value: String): MealType {
        return MealType.valueOf(value)
    }

    @TypeConverter
    fun fromAchievementType(value: AchievementType): String {
        return value.name
    }

    @TypeConverter
    fun toAchievementType(value: String): AchievementType {
        return AchievementType.valueOf(value)
    }

    @TypeConverter
    fun fromChallengeType(value: ChallengeType): String {
        return value.name
    }

    @TypeConverter
    fun toChallengeType(value: String): ChallengeType {
        return ChallengeType.valueOf(value)
    }
} 