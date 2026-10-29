package com.darkmisfit.omni.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val OmniDarkColorScheme = darkColorScheme(
    primary = PurplishBlue,
    onPrimary = Color.White,
    primaryContainer = ObsidianPurple,
    onPrimaryContainer = BabyBlue,
    secondary = BabyBlue,
    onSecondary = Color.Black,
    secondaryContainer = BorderPurple,
    onSecondaryContainer = TextPrimary,
    tertiary = BabyBlue,
    onTertiary = Color.Black,
    background = CanvasRootBlack,
    onBackground = TextPrimary,
    surface = ObsidianPurple,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceCard,
    onSurfaceVariant = TextMuted,
    outline = BorderPurple,
    outlineVariant = BorderHighlight
)

@Composable
fun OmniTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = OmniDarkColorScheme,
        typography = OmniTypography,
        content = content
    )
}
