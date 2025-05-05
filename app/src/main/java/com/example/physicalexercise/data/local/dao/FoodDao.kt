package com.example.physicalexercise.data.local.dao

import androidx.room.*
import com.example.physicalexercise.data.local.entities.FoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM foods WHERE id = :foodId")
    fun getFoodById(foodId: String): Flow<FoodEntity?>

    @Query("SELECT * FROM foods WHERE barcode = :barcode")
    fun getFoodByBarcode(barcode: String): Flow<FoodEntity?>

    @Query("SELECT * FROM foods WHERE name LIKE '%' || :query || '%'")
    fun searchFoods(query: String): Flow<List<FoodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: FoodEntity)

    @Update
    suspend fun updateFood(food: FoodEntity)

    @Delete
    suspend fun deleteFood(food: FoodEntity)

    @Query("SELECT * FROM foods WHERE isCustom = 1")
    fun getCustomFoods(): Flow<List<FoodEntity>>
} 