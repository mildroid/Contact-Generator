package com.mildroid.contactgenerator.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Dark,
    primaryVariant = Dark,
    secondary = Teal200
)

/**
 * Main Composable for Terminal View.
 */
@Composable
fun ComposeMainTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}