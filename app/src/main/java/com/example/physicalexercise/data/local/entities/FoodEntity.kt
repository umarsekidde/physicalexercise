package com.example.physicalexercise.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class FoodEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val barcode: String,
    val calories: Int,
    val protein: Float,
    val carbs: Float,
    val fat: Float,
    val servingSize: Float,
    val servingUnit: String,
    val isCustom: Boolean
) 