package com.example.physicalexercise.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.physicalexercise.data.models.MealType
import java.util.Date

@Entity(
    tableName = "food_entries",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = FoodEntity::class,
            parentColumns = ["id"],
            childColumns = ["foodId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId"), Index("foodId")]
)
data class FoodEntryEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val foodId: String,
    val date: Date,
    val quantity: Float,
    val mealType: MealType
) 