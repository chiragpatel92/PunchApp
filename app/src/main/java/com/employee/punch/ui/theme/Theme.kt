package com.employee.punch.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = TextPrimary,

    secondary = Secondary,
    onSecondary = Color.White,

    background = BackgroundColor,
    onBackground = TextPrimary,

    surface = SurfaceColor,
    onSurface = TextPrimary,

    error = ErrorColor,
)

/*private val DarkColors = darkColorScheme(
    primary = PrimaryLight,
    onPrimary = Color.Black,

    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),

    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,

    error = ErrorColor
)*/

@Composable
fun EmployeePunchTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
//    val colorScheme = if (darkTheme) DarkColors else LightColors
    val colorScheme = LightColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}