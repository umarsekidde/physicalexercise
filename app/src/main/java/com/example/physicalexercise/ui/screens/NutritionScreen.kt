package com.example.physicalexercise.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicalexercise.data.models.Food
import com.example.physicalexercise.data.models.MealType
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionScreen(navController: NavController) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showAddFood by remember { mutableStateOf(false) }
    var selectedMealType by remember { mutableStateOf<MealType?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nutrition") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showAddFood = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Food")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Date selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { selectedDate = selectedDate.minusDays(1) }) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = "Previous Day")
                }
                Text(
                    text = selectedDate.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = { selectedDate = selectedDate.plusDays(1) }) {
                    Icon(Icons.Default.ChevronRight, contentDescription = "Next Day")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Meal type selector
            Text("Meal Type", style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MealType.values().forEach { mealType ->
                    FilterChip(
                        selected = selectedMealType == mealType,
                        onClick = { selectedMealType = mealType },
                        label = { Text(mealType.name) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Food list
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // TODO: Add food items
                items(emptyList<Food>()) { food ->
                    FoodItem(food = food)
                }
            }
        }
    }

    if (showAddFood) {
        AddFoodDialog(
            onDismiss = { showAddFood = false },
            onFoodAdded = { food ->
                // TODO: Save food
                showAddFood = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItem(food: Food) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { /* TODO: Show food details */ }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = food.name,
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${food.calories} calories",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = food.mealType.name,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = "Protein: ${food.protein}g | Carbs: ${food.carbs}g | Fat: ${food.fat}g",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodDialog(
    onDismiss: () -> Unit,
    onFoodAdded: (Food) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var carbs by remember { mutableStateOf("") }
    var fat by remember { mutableStateOf("") }
    var selectedMealType by remember { mutableStateOf<MealType?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Food") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Food Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = calories,
                    onValueChange = { calories = it },
                    label = { Text("Calories") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = protein,
                        onValueChange = { protein = it },
                        label = { Text("Protein (g)") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = carbs,
                        onValueChange = { carbs = it },
                        label = { Text("Carbs (g)") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = fat,
                        onValueChange = { fat = it },
                        label = { Text("Fat (g)") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Text("Meal Type", style = MaterialTheme.typography.titleMedium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MealType.values().forEach { mealType ->
                        FilterChip(
                            selected = selectedMealType == mealType,
                            onClick = { selectedMealType = mealType },
                            label = { Text(mealType.name) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    // TODO: Create food
                    onDismiss()
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
} 