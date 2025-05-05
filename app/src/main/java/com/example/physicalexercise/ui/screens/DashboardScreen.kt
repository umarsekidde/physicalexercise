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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.physicalexercise.data.models.Progress
import com.example.physicalexercise.navigation.Screen
import com.example.physicalexercise.ui.viewmodels.DashboardViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val todayStats by viewModel.todayStats.collectAsState()
    val recentActivity by viewModel.recentActivity.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                actions = {
                    IconButton(onClick = { /* TODO: Open settings */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    QuickStatsCard(todayStats)
                }

                item {
                    Text(
                        text = "Features",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(navigationItems) { item ->
                    NavigationCard(
                        title = item.title,
                        icon = item.icon,
                        onClick = { navController.navigate(item.route) }
                    )
                }

                item {
                    Text(
                        text = "Recent Activity",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(recentActivity) { progress ->
                    ActivityItem(progress = progress)
                }
            }
        }
    }

    error?.let { errorMessage ->
        AlertDialog(
            onDismissRequest = { viewModel.clearError() },
            title = { Text("Error") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = { viewModel.clearError() }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun QuickStatsCard(stats: TodayStats) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Today's Summary",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    icon = Icons.Default.DirectionsWalk,
                    value = stats.steps.toString(),
                    label = "Steps"
                )
                StatItem(
                    icon = Icons.Default.FitnessCenter,
                    value = stats.workoutMinutes.toString(),
                    label = "Minutes"
                )
                StatItem(
                    icon = Icons.Default.LocalFireDepartment,
                    value = stats.caloriesBurned.toString(),
                    label = "Calories"
                )
            }
        }
    }
}

@Composable
fun StatItem(
    icon: ImageVector,
    value: String,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun ActivityItem(progress: Progress) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when (progress.mood) {
                    com.example.physicalexercise.data.models.Mood.VERY_HAPPY -> Icons.Default.SentimentVerySatisfied
                    com.example.physicalexercise.data.models.Mood.HAPPY -> Icons.Default.SentimentSatisfied
                    com.example.physicalexercise.data.models.Mood.NEUTRAL -> Icons.Default.SentimentNeutral
                    com.example.physicalexercise.data.models.Mood.SAD -> Icons.Default.SentimentDissatisfied
                    com.example.physicalexercise.data.models.Mood.VERY_SAD -> Icons.Default.SentimentVeryDissatisfied
                },
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${progress.steps} steps",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${progress.caloriesBurned} calories burned",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = progress.date.toString(),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

private data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

private val navigationItems = listOf(
    NavigationItem(
        title = "Profile",
        icon = Icons.Default.Person,
        route = Screen.Profile.route
    ),
    NavigationItem(
        title = "Progress",
        icon = Icons.Default.TrendingUp,
        route = Screen.Progress.route
    ),
    NavigationItem(
        title = "Workouts",
        icon = Icons.Default.FitnessCenter,
        route = Screen.Workouts.route
    ),
    NavigationItem(
        title = "Nutrition",
        icon = Icons.Default.Restaurant,
        route = Screen.Nutrition.route
    ),
    NavigationItem(
        title = "Achievements",
        icon = Icons.Default.EmojiEvents,
        route = Screen.Achievements.route
    )
) 