package com.peumax.calculatetools.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.peumax.calculatetools.R
import kotlinx.coroutines.delay

private const val SPLASH_DURATION_MS = 2000L

@Composable
fun SplashScreen(onNavigateToCalculator: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(SPLASH_DURATION_MS)
        onNavigateToCalculator()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0D2B6E),
                        Color(0xFF1565C0),
                        Color(0xFF0D2B6E)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_peumax_spray),
                contentDescription = "Peumax logo",
                modifier = Modifier.size(180.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "PEUMAX",
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 4.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "100% BRASILEIRA",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 3.sp
            )
        }
    }
}
