package com.mediaflix.app.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable

val DarkBackground = Color(0xFF0D0D0D)
val DarkSurface = Color(0xFF1A1A2E)
val Accent = Color(0xFFE50914)
val AccentSecondary = Color(0xFFFF6B35)
val Gold = Color(0xFFFFD700)
val TextPrimary = Color(0xFFF5F5F5)
val TextSecondary = Color(0xFFB0B0B0)

private val MediaFlixColorScheme = darkColorScheme(
    primary = Accent,
    secondary = AccentSecondary,
    tertiary = Gold,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = Color(0xFF252542),
    onPrimary = TextPrimary,
    onSecondary = TextPrimary,
    onTertiary = DarkBackground,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
)

@Composable
fun MediaFlixTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MediaFlixColorScheme,
        content = content
    )
}