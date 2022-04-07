package ru.alexgladkov.common.compose.theme.colors

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

val LocalColors = staticCompositionLocalOf<OdysseyColors> {
    error("No colors provided")
}