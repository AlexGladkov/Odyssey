package ru.alexgladkov.shared.theme.colors

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class OdysseyColors(
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val tintColor: Color,
    val controlColor: Color,
    val errorColor: Color
)

internal val LocalColors = staticCompositionLocalOf<OdysseyColors> {
    error("No colors provided")
}