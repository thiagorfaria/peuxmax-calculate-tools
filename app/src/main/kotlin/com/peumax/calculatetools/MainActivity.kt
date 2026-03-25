package com.peumax.calculatetools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.peumax.calculatetools.ui.screens.CalculatorScreen
import com.peumax.calculatetools.ui.screens.SplashScreen
import com.peumax.calculatetools.ui.theme.PeumaxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PeumaxTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ) {
                    composable("splash") {
                        SplashScreen(
                            onNavigateToCalculator = {
                                navController.navigate("calculator") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("calculator") {
                        CalculatorScreen()
                    }
                }
            }
        }
    }
}
