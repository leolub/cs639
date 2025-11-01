package com.example.circleapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val OneScheme = darkColorScheme(
    primary = RedPrimary,
    secondary = RedSecondary,
    tertiary = RedTertiary,
    background = Bg,
    surface = Surface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = OnBg,
    onSurface = OnSurface,
    error = Error,
    onError = OnError
)

@Composable
fun CircleAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = OneScheme,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
