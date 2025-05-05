package com.example.physicalexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.physicalexercise.navigation.NavGraph
import com.example.physicalexercise.ui.theme.PhysicalExerciseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhysicalExerciseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PhysicalExerciseApp()
                }
            }
        }
    }
}

@Composable
fun PhysicalExerciseApp() {
    val navController = rememberNavController()
    NavGraph(navController = navController)
}