package ru.alexgladkov.odyssey.compose.base.local

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

class BottomConfiguration(
    val backgroundColor: Color,
    val selectedColor: Color,
    val unselectedColor: Color,
    val elevation: Dp,
)

val LocalBottomConfiguration = staticCompositionLocalOf<BottomConfiguration> { error("No default config provided") }