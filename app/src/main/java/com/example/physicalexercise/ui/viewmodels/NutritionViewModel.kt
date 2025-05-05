package com.example.physicalexercise.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.physicalexercise.data.models.Food
import com.example.physicalexercise.data.models.MealType
import com.example.physicalexercise.data.repositories.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class NutritionViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _selectedMealType = MutableStateFlow<MealType?>(null)
    val selectedMealType: StateFlow<MealType?> = _selectedMealType.asStateFlow()

    private val _foods = MutableStateFlow<List<Food>>(emptyList())
    val foods: StateFlow<List<Food>> = _foods.asStateFlow()

    private val _dailySummary = MutableStateFlow(DailySummary())
    val dailySummary: StateFlow<DailySummary> = _dailySummary.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadFoods()
    }

    fun setDate(date: LocalDate) {
        _selectedDate.value = date
        loadFoods()
    }

    fun setMealType(mealType: MealType?) {
        _selectedMealType.value = mealType
        loadFoods()
    }

    private fun loadFoods() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val foods = _selectedMealType.value?.let { mealType ->
                    foodRepository.getFoodsByDateAndMealType(_selectedDate.value, mealType)
                } ?: foodRepository.getFoodsByDate(_selectedDate.value)
                _foods.value = foods
                updateDailySummary(foods)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load foods"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun updateDailySummary(foods: List<Food>) {
        val summary = foods.fold(DailySummary()) { acc, food ->
            acc.copy(
                totalCalories = acc.totalCalories + food.calories,
                totalProtein = acc.totalProtein + food.protein,
                totalCarbs = acc.totalCarbs + food.carbs,
                totalFat = acc.totalFat + food.fat
            )
        }
        _dailySummary.value = summary
    }

    fun addFood(
        name: String,
        calories: Int,
        protein: Float,
        carbs: Float,
        fat: Float,
        mealType: MealType
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val food = Food(
                    name = name,
                    calories = calories,
                    protein = protein,
                    carbs = carbs,
                    fat = fat,
                    mealType = mealType,
                    date = _selectedDate.value
                )
                foodRepository.insertFood(food)
                loadFoods()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to add food"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteFood(food: Food) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                foodRepository.deleteFood(food)
                loadFoods()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to delete food"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun retry() {
        loadFoods()
    }
}

data class DailySummary(
    val totalCalories: Int = 0,
    val totalProtein: Float = 0f,
    val totalCarbs: Float = 0f,
    val totalFat: Float = 0f
) 