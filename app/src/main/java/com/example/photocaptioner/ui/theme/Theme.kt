package com.example.photocaptioner.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = DarkBlue,
    onPrimary = Color.White,
    background = Black,
    surface = LightGreyDarkVersion,
    onSurface = Color.White,
    onBackground = Color.White
)

private val LightColorPalette = lightColors(
    primary = LightBlue,
    onPrimary = Color.Black,
    background = White,
    surface = LightGreyLightVersion,
    onSurface = Color.Black,
    onBackground = Color.Black
)

@Composable
fun PhotoCaptionerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}