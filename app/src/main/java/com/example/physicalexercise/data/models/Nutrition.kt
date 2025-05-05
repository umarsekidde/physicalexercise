package com.example.physicalexercise.data.models

import java.util.Date

data class FoodEntry(
    val id: String = "",
    val userId: String = "",
    val date: Date = Date(),
    val food: Food = Food(),
    val quantity: Float = 0f,
    val mealType: MealType = MealType.BREAKFAST
)

data class Food(
    val id: String = "",
    val name: String = "",
    val barcode: String = "",
    val calories: Int = 0,
    val protein: Float = 0f,
    val carbs: Float = 0f,
    val fat: Float = 0f,
    val servingSize: Float = 0f,
    val servingUnit: String = "g",
    val isCustom: Boolean = false
)

data class MealPlan(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val description: String = "",
    val meals: List<Meal> = emptyList(),
    val totalCalories: Int = 0,
    val dietaryPreference: DietaryPreference = DietaryPreference.REGULAR
)

data class Meal(
    val id: String = "",
    val name: String = "",
    val foods: List<Food> = emptyList(),
    val totalCalories: Int = 0,
    val mealType: MealType = MealType.BREAKFAST
)

enum class MealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK
} 