package com.example.physicalexercise.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.physicalexercise.ui.screens.*

sealed class Screen(val route: String) {
    object Profile : Screen("profile")
    object Progress : Screen("progress")
    object Workouts : Screen("workouts")
    object Nutrition : Screen("nutrition")
    object Achievements : Screen("achievements")
    object Dashboard : Screen("dashboard")
    object Settings : Screen("settings")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Dashboard.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onNavigateToProgress = { navController.navigate(Screen.Progress.route) },
                onNavigateToWorkouts = { navController.navigate(Screen.Workouts.route) },
                onNavigateToNutrition = { navController.navigate(Screen.Nutrition.route) },
                onNavigateToAchievements = { navController.navigate(Screen.Achievements.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Progress.route) {
            ProgressScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Workouts.route) {
            WorkoutsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Nutrition.route) {
            NutritionScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Achievements.route) {
            AchievementsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
} 