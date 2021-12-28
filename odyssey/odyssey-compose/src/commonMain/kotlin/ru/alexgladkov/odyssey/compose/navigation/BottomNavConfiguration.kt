package ru.alexgladkov.odyssey.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

abstract class BottomNavModel {
    abstract val bottomNavConfiguration: BottomNavConfiguration
        @Composable get
}

data class BottomNavConfiguration(
    val backgroundColor: Color,
    val selectedColor: Color,
    val unselectedColor: Color
)