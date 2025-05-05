package com.example.physicalexercise.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.physicalexercise.ui.viewmodels.SettingsViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val isOfflineMode by viewModel.isOfflineMode.collectAsState()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    val reminderTime by viewModel.reminderTime.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var showClearDataDialog by remember { mutableStateOf(false) }
    var showTimePickerDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Theme Settings
                Text(
                    text = "Appearance",
                    style = MaterialTheme.typography.titleLarge
                )
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { viewModel.setDarkMode(it) },
                    modifier = Modifier.fillMaxWidth(),
                    thumbContent = {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    }
                )

                Divider()

                // Offline Mode
                Text(
                    text = "Data",
                    style = MaterialTheme.typography.titleLarge
                )
                Switch(
                    checked = isOfflineMode,
                    onCheckedChange = { viewModel.setOfflineMode(it) },
                    modifier = Modifier.fillMaxWidth(),
                    thumbContent = {
                        Icon(
                            imageVector = if (isOfflineMode) Icons.Default.CloudOff else Icons.Default.Cloud,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    }
                )

                Divider()

                // Notifications
                Text(
                    text = "Notifications",
                    style = MaterialTheme.typography.titleLarge
                )
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { viewModel.setNotificationsEnabled(it) },
                    modifier = Modifier.fillMaxWidth(),
                    thumbContent = {
                        Icon(
                            imageVector = if (notificationsEnabled) Icons.Default.Notifications else Icons.Default.NotificationsOff,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    }
                )

                if (notificationsEnabled) {
                    OutlinedButton(
                        onClick = { showTimePickerDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Daily Reminder: $reminderTime")
                    }
                }

                Divider()

                // Data Management
                Text(
                    text = "Data Management",
                    style = MaterialTheme.typography.titleLarge
                )
                Button(
                    onClick = { showClearDataDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Clear All Data")
                }
            }
        }
    }

    if (showClearDataDialog) {
        AlertDialog(
            onDismissRequest = { showClearDataDialog = false },
            title = { Text("Clear All Data") },
            text = { Text("Are you sure you want to clear all app data? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.clearAllData()
                        showClearDataDialog = false
                    }
                ) {
                    Text("Clear")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDataDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showTimePickerDialog) {
        val time = LocalTime.parse(reminderTime, DateTimeFormatter.ofPattern("HH:mm"))
        var selectedHour by remember { mutableStateOf(time.hour) }
        var selectedMinute by remember { mutableStateOf(time.minute) }

        AlertDialog(
            onDismissRequest = { showTimePickerDialog = false },
            title = { Text("Set Reminder Time") },
            text = {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Hour picker
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(onClick = { selectedHour = (selectedHour + 1) % 24 }) {
                                Icon(Icons.Default.KeyboardArrowUp, "Increase hour")
                            }
                            Text(
                                text = "%02d".format(selectedHour),
                                style = MaterialTheme.typography.headlineMedium
                            )
                            IconButton(onClick = { selectedHour = (selectedHour + 23) % 24 }) {
                                Icon(Icons.Default.KeyboardArrowDown, "Decrease hour")
                            }
                        }

                        Text(
                            text = ":",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        // Minute picker
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(onClick = { selectedMinute = (selectedMinute + 1) % 60 }) {
                                Icon(Icons.Default.KeyboardArrowUp, "Increase minute")
                            }
                            Text(
                                text = "%02d".format(selectedMinute),
                                style = MaterialTheme.typography.headlineMedium
                            )
                            IconButton(onClick = { selectedMinute = (selectedMinute + 59) % 60 }) {
                                Icon(Icons.Default.KeyboardArrowDown, "Decrease minute")
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val newTime = "%02d:%02d".format(selectedHour, selectedMinute)
                        viewModel.setReminderTime(newTime)
                        showTimePickerDialog = false
                    }
                ) {
                    Text("Set")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePickerDialog = false }) {
                    Text("Cancel")
                }
            }
        )
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