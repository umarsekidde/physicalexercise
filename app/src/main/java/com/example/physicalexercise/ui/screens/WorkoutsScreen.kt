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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.physicalexercise.data.models.Workout
import com.example.physicalexercise.data.models.WorkoutDifficulty
import com.example.physicalexercise.ui.viewmodels.WorkoutsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutsScreen(
    navController: NavController,
    viewModel: WorkoutsViewModel = hiltViewModel()
) {
    val workouts by viewModel.workouts.collectAsState()
    val selectedDifficulty by viewModel.selectedDifficulty.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    var showCreateDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workouts") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showCreateDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Workout")
                    }
                }
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                error?.let { errorMessage ->
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // Difficulty filter
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = selectedDifficulty == null,
                        onClick = { viewModel.setDifficulty(null) },
                        label = { Text("All") }
                    )
                    WorkoutDifficulty.values().forEach { difficulty ->
                        FilterChip(
                            selected = selectedDifficulty == difficulty,
                            onClick = { viewModel.setDifficulty(difficulty) },
                            label = { Text(difficulty.name.replace("_", " ")) }
                        )
                    }
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(workouts) { workout ->
                        WorkoutItem(
                            workout = workout,
                            onDelete = { viewModel.deleteWorkout(workout) }
                        )
                    }
                }
            }
        }
    }

    if (showCreateDialog) {
        CreateWorkoutDialog(
            onDismiss = { showCreateDialog = false },
            onConfirm = { name, description, difficulty, duration, caloriesBurned ->
                viewModel.addWorkout(
                    name = name,
                    description = description,
                    difficulty = difficulty,
                    duration = duration,
                    caloriesBurned = caloriesBurned
                )
                showCreateDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutItem(
    workout: Workout,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = workout.name,
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
            Text(
                text = workout.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Difficulty: ${workout.difficulty.name.replace("_", " ")}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Duration: ${workout.duration} min",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Calories: ${workout.caloriesBurned}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateWorkoutDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, WorkoutDifficulty, Int, Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf(WorkoutDifficulty.BEGINNER) }
    var duration by remember { mutableStateOf("") }
    var caloriesBurned by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create Workout") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Difficulty",
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    WorkoutDifficulty.values().forEach { difficulty ->
                        FilterChip(
                            selected = selectedDifficulty == difficulty,
                            onClick = { selectedDifficulty = difficulty },
                            label = { Text(difficulty.name.replace("_", " ")) }
                        )
                    }
                }
                OutlinedTextField(
                    value = duration,
                    onValueChange = { duration = it },
                    label = { Text("Duration (minutes)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = caloriesBurned,
                    onValueChange = { caloriesBurned = it },
                    label = { Text("Calories Burned") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(
                        name,
                        description,
                        selectedDifficulty,
                        duration.toIntOrNull() ?: 0,
                        caloriesBurned.toIntOrNull() ?: 0
                    )
                }
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
} 