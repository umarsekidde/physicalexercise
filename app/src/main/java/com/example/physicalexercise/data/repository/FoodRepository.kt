package com.example.physicalexercise.data.repository

import com.example.physicalexercise.data.local.dao.FoodDao
import com.example.physicalexercise.data.local.dao.FoodEntryDao
import com.example.physicalexercise.data.local.entities.FoodEntity
import com.example.physicalexercise.data.local.entities.FoodEntryEntity
import com.example.physicalexercise.data.models.Food
import com.example.physicalexercise.data.models.FoodEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.util.Date
import java.util.UUID

class FoodRepository(
    private val foodDao: FoodDao,
    private val foodEntryDao: FoodEntryDao
) {

    fun getFoodEntriesForUser(userId: String, date: Date): Flow<List<FoodEntry>> {
        return foodEntryDao.getFoodEntriesForUserOnDate(userId, date).map { entities ->
            entities.map { it.toFoodEntry() }
        }
    }

    fun getFoodEntriesForUserInDateRange(userId: String, startDate: Date, endDate: Date): Flow<List<FoodEntry>> {
        return foodEntryDao.getFoodEntriesForUserInDateRange(userId, startDate, endDate).map { entities ->
            entities.map { it.toFoodEntry() }
        }
    }

    fun searchFoods(query: String): Flow<List<Food>> {
        return foodDao.searchFoods(query).map { entities ->
            entities.map { it.toFood() }
        }
    }

    fun getFoodByBarcode(barcode: String): Flow<Food?> {
        return foodDao.getFoodByBarcode(barcode).map { entity ->
            entity?.toFood()
        }
    }

    suspend fun addFoodEntry(
        userId: String,
        foodId: String,
        quantity: Float,
        mealType: String,
        date: Date = Date()
    ): FoodEntry {
        val foodEntry = FoodEntryEntity(
            id = UUID.randomUUID().toString(),
            userId = userId,
            foodId = foodId,
            date = date,
            quantity = quantity,
            mealType = com.example.physicalexercise.data.models.MealType.valueOf(mealType)
        )
        foodEntryDao.insertFoodEntry(foodEntry)
        return foodEntry.toFoodEntry()
    }

    suspend fun addCustomFood(
        name: String,
        barcode: String,
        calories: Int,
        protein: Float,
        carbs: Float,
        fat: Float,
        servingSize: Float,
        servingUnit: String
    ): Food {
        val food = FoodEntity(
            id = UUID.randomUUID().toString(),
            name = name,
            barcode = barcode,
            calories = calories,
            protein = protein,
            carbs = carbs,
            fat = fat,
            servingSize = servingSize,
            servingUnit = servingUnit,
            isCustom = true
        )
        foodDao.insertFood(food)
        return food.toFood()
    }

    suspend fun updateFoodEntry(foodEntry: FoodEntry) {
        foodEntryDao.updateFoodEntry(foodEntry.toEntity())
    }

    suspend fun deleteFoodEntry(foodEntry: FoodEntry) {
        foodEntryDao.deleteFoodEntry(foodEntry.toEntity())
    }

    private fun FoodEntity.toFood(): Food {
        return Food(
            id = id,
            name = name,
            barcode = barcode,
            calories = calories,
            protein = protein,
            carbs = carbs,
            fat = fat,
            servingSize = servingSize,
            servingUnit = servingUnit,
            isCustom = isCustom
        )
    }

    private fun Food.toEntity(): FoodEntity {
        return FoodEntity(
            id = id,
            name = name,
            barcode = barcode,
            calories = calories,
            protein = protein,
            carbs = carbs,
            fat = fat,
            servingSize = servingSize,
            servingUnit = servingUnit,
            isCustom = isCustom
        )
    }

    private fun FoodEntryEntity.toFoodEntry(): FoodEntry {
        return FoodEntry(
            id = id,
            userId = userId,
            foodId = foodId,
            date = date,
            quantity = quantity,
            mealType = mealType
        )
    }

    private fun FoodEntry.toEntity(): FoodEntryEntity {
        return FoodEntryEntity(
            id = id,
            userId = userId,
            foodId = foodId,
            date = date,
            quantity = quantity,
            mealType = mealType
        )
    }
} 