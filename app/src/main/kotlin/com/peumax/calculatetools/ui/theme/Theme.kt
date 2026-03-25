package com.peumax.calculatetools.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = PeumaxNavy,
    secondary = PeumaxBlue,
    tertiary = PeumaxLightBlue,
    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = TextPrimaryDark,
    onSecondary = TextPrimaryDark,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

private val DarkColorScheme = darkColorScheme(
    primary = PeumaxLightBlue,
    secondary = PeumaxBlue,
    tertiary = PeumaxNavy,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = TextPrimary,
    onSecondary = TextPrimaryDark,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark
)

@Composable
fun PeumaxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
