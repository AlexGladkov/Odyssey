package ru.alexgladkov.common.compose.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.core.navigation.bottom_bar_navigation.BottomNavConfiguration
import ru.alexgladkov.odyssey.core.navigation.bottom_bar_navigation.BottomNavModel

class BottomConfiguration : BottomNavModel() {

    override val bottomNavConfiguration: BottomNavConfiguration
        @Composable
        get() {
            return BottomNavConfiguration(
                backgroundColor = Color.White,
                selectedColor = Color.DarkGray,
                unselectedColor = Color.LightGray
            )
        }
}