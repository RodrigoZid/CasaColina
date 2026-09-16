package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val CriolloColorScheme = lightColorScheme(
    primary = CriolloPrimary,
    onPrimary = CriolloOnPrimary,
    primaryContainer = CriolloPrimaryContainer,
    onPrimaryContainer = CriolloOnPrimaryContainer,
    secondary = CriolloSecondary,
    onSecondary = CriolloOnSecondary,
    secondaryContainer = CriolloSecondaryContainer,
    onSecondaryContainer = CriolloOnSecondaryContainer,
    tertiary = CriolloTertiary,
    onTertiary = CriolloOnTertiary,
    tertiaryContainer = CriolloTertiaryContainer,
    onTertiaryContainer = CriolloOnTertiaryContainer,
    background = CriolloBackground,
    onBackground = CriolloOnBackground,
    surface = CriolloSurface,
    onSurface = CriolloOnSurface,
    surfaceVariant = CriolloSurfaceVariant,
    onSurfaceVariant = CriolloOnSurfaceVariant,
    outline = CriolloOutline,
    outlineVariant = CriolloOutlineVariant,
    error = CriolloError,
    onError = CriolloOnError
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Preserve authentic criollo warm editorial palette
    MaterialTheme(
        colorScheme = CriolloColorScheme,
        typography = CriolloTypography,
        content = content
    )
}
