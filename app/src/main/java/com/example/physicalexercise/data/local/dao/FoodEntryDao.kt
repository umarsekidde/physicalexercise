package com.example.physicalexercise.data.local.dao

import androidx.room.*
import com.example.physicalexercise.data.local.entities.FoodEntryEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface FoodEntryDao {
    @Query("SELECT * FROM food_entries WHERE userId = :userId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getFoodEntriesForUserInDateRange(userId: String, startDate: Date, endDate: Date): Flow<List<FoodEntryEntity>>

    @Query("SELECT * FROM food_entries WHERE userId = :userId AND date = :date")
    fun getFoodEntriesForUserOnDate(userId: String, date: Date): Flow<List<FoodEntryEntity>>

    @Query("SELECT * FROM food_entries WHERE id = :entryId")
    fun getFoodEntryById(entryId: String): Flow<FoodEntryEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodEntry(foodEntry: FoodEntryEntity)

    @Update
    suspend fun updateFoodEntry(foodEntry: FoodEntryEntity)

    @Delete
    suspend fun deleteFoodEntry(foodEntry: FoodEntryEntity)

    @Query("SELECT * FROM food_entries WHERE userId = :userId AND mealType = :mealType AND date = :date")
    fun getFoodEntriesByMealType(userId: String, mealType: String, date: Date): Flow<List<FoodEntryEntity>>
} 