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

sealed class Route(val path: String) {
    data object Splash : Route("splash")
    data object Calculator : Route("calculator")
}

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
                    startDestination = Route.Splash.path
                ) {
                    composable(Route.Splash.path) {
                        SplashScreen(
                            onNavigateToCalculator = {
                                navController.navigate(Route.Calculator.path) {
                                    popUpTo(Route.Splash.path) { inclusive = true }
                                }
                            }
                        )
                    }
                    composable(Route.Calculator.path) {
                        CalculatorScreen()
                    }
                }
            }
        }
    }
}
