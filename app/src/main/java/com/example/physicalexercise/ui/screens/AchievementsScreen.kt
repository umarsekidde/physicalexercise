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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicalexercise.data.models.Achievement
import com.example.physicalexercise.data.models.AchievementType
import com.example.physicalexercise.data.models.Challenge
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementsScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }
    var showCreateChallenge by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Achievements") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (selectedTab == 1) {
                        IconButton(onClick = { showCreateChallenge = true }) {
                            Icon(Icons.Default.Add, contentDescription = "Create Challenge")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Achievements") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Challenges") }
                )
            }

            when (selectedTab) {
                0 -> AchievementsTab()
                1 -> ChallengesTab()
            }
        }
    }

    if (showCreateChallenge) {
        CreateChallengeDialog(
            onDismiss = { showCreateChallenge = false },
            onChallengeCreated = { challenge ->
                // TODO: Save challenge
                showCreateChallenge = false
            }
        )
    }
}

@Composable
fun AchievementsTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Progress summary
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
                    text = "Your Progress",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Level 5",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "2,500 XP",
                    style = MaterialTheme.typography.bodyLarge
                )
                LinearProgressIndicator(
                    progress = 0.75f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Text(
                    text = "750 XP to next level",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Achievements list
        Text(
            text = "Recent Achievements",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // TODO: Add achievement items
            items(emptyList<Achievement>()) { achievement ->
                AchievementItem(achievement = achievement)
            }
        }
    }
}

@Composable
fun ChallengesTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Active challenges
        Text(
            text = "Active Challenges",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // TODO: Add challenge items
            items(emptyList<Challenge>()) { challenge ->
                ChallengeItem(challenge = challenge)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementItem(achievement: Achievement) {
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
                imageVector = when (achievement.type) {
                    AchievementType.WORKOUT -> Icons.Default.FitnessCenter
                    AchievementType.NUTRITION -> Icons.Default.Restaurant
                    AchievementType.STEPS -> Icons.Default.DirectionsWalk
                    AchievementType.STREAK -> Icons.Default.LocalFireDepartment
                },
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = achievement.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = achievement.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "+${achievement.xpReward} XP",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeItem(challenge: Challenge) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { /* TODO: Show challenge details */ }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = challenge.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = challenge.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${challenge.participants.size} participants",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "${challenge.daysLeft} days left",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            LinearProgressIndicator(
                progress = challenge.progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateChallengeDialog(
    onDismiss: () -> Unit,
    onChallengeCreated: (Challenge) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create Challenge") },
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
                    label = { Text("Challenge Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = duration,
                    onValueChange = { duration = it },
                    label = { Text("Duration (days)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = goal,
                    onValueChange = { goal = it },
                    label = { Text("Goal") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    // TODO: Create challenge
                    onDismiss()
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