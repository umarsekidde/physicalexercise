package com.example.physicalexercise.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.physicalexercise.data.models.DietaryPreference
import com.example.physicalexercise.data.models.FitnessGoal
import com.example.physicalexercise.ui.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profile by viewModel.profile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var name by remember { mutableStateOf(profile?.name ?: "") }
    var age by remember { mutableStateOf(profile?.age?.toString() ?: "") }
    var weight by remember { mutableStateOf(profile?.weight?.toString() ?: "") }
    var height by remember { mutableStateOf(profile?.height?.toString() ?: "") }
    var gender by remember { mutableStateOf(profile?.gender ?: "") }
    var selectedGoal by remember { mutableStateOf(profile?.fitnessGoal ?: FitnessGoal.WEIGHT_LOSS) }
    var selectedDietaryPreference by remember { mutableStateOf(profile?.dietaryPreference ?: DietaryPreference.REGULAR) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
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
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Age") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Weight (kg)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = height,
                    onValueChange = { height = it },
                    label = { Text("Height (cm)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = gender,
                    onValueChange = { gender = it },
                    label = { Text("Gender") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Fitness Goal",
                    style = MaterialTheme.typography.titleMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FitnessGoal.values().forEach { goal ->
                        FilterChip(
                            selected = selectedGoal == goal,
                            onClick = { selectedGoal = goal },
                            label = { Text(goal.name.replace("_", " ")) }
                        )
                    }
                }

                Text(
                    text = "Dietary Preference",
                    style = MaterialTheme.typography.titleMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DietaryPreference.values().forEach { preference ->
                        FilterChip(
                            selected = selectedDietaryPreference == preference,
                            onClick = { selectedDietaryPreference = preference },
                            label = { Text(preference.name.replace("_", " ")) }
                        )
                    }
                }

                Button(
                    onClick = {
                        viewModel.updateProfile(
                            name = name,
                            age = age.toIntOrNull() ?: 0,
                            weight = weight.toFloatOrNull() ?: 0f,
                            height = height.toFloatOrNull() ?: 0f,
                            gender = gender,
                            fitnessGoal = selectedGoal,
                            dietaryPreference = selectedDietaryPreference
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Profile")
                }
            }
        }
    }
} 