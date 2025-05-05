package com.example.physicalexercise.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.physicalexercise.data.models.Mood
import com.example.physicalexercise.ui.viewmodels.ProgressViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    navController: NavController,
    viewModel: ProgressViewModel = hiltViewModel()
) {
    val todayProgress by viewModel.todayProgress.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var weight by remember { mutableStateOf(todayProgress?.weight?.toString() ?: "") }
    var steps by remember { mutableStateOf(todayProgress?.steps?.toString() ?: "") }
    var waterIntake by remember { mutableStateOf(todayProgress?.waterIntake?.toString() ?: "") }
    var sleepHours by remember { mutableStateOf(todayProgress?.sleepHours?.toString() ?: "") }
    var selectedMood by remember { mutableStateOf(todayProgress?.mood ?: Mood.NEUTRAL) }
    var energyLevel by remember { mutableStateOf(todayProgress?.energyLevel?.toFloat() ?: 5f) }
    var workoutMinutes by remember { mutableStateOf(todayProgress?.workoutMinutes?.toString() ?: "") }
    var caloriesBurned by remember { mutableStateOf(todayProgress?.caloriesBurned?.toString() ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Progress") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                error?.let { errorMessage ->
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Weight (kg)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = steps,
                    onValueChange = { steps = it },
                    label = { Text("Steps") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = waterIntake,
                    onValueChange = { waterIntake = it },
                    label = { Text("Water Intake (liters)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = sleepHours,
                    onValueChange = { sleepHours = it },
                    label = { Text("Sleep Hours") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = workoutMinutes,
                    onValueChange = { workoutMinutes = it },
                    label = { Text("Workout Minutes") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = caloriesBurned,
                    onValueChange = { caloriesBurned = it },
                    label = { Text("Calories Burned") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Mood",
                    style = MaterialTheme.typography.titleMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Mood.values().forEach { mood ->
                        FilterChip(
                            selected = selectedMood == mood,
                            onClick = { selectedMood = mood },
                            label = { Text(mood.name.replace("_", " ")) }
                        )
                    }
                }

                Text(
                    text = "Energy Level: ${energyLevel.toInt()}",
                    style = MaterialTheme.typography.titleMedium
                )

                Slider(
                    value = energyLevel,
                    onValueChange = { energyLevel = it },
                    valueRange = 1f..10f,
                    steps = 8,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        viewModel.updateProgress(
                            weight = weight.toFloatOrNull() ?: 0f,
                            steps = steps.toIntOrNull() ?: 0,
                            waterIntake = waterIntake.toFloatOrNull() ?: 0f,
                            sleepHours = sleepHours.toFloatOrNull() ?: 0f,
                            mood = selectedMood,
                            energyLevel = energyLevel.toInt(),
                            workoutMinutes = workoutMinutes.toIntOrNull() ?: 0,
                            caloriesBurned = caloriesBurned.toIntOrNull() ?: 0
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Progress")
                }
            }
        }
    }
} 