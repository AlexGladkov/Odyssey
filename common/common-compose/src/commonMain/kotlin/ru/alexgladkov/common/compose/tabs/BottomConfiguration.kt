package ru.alexgladkov.common.compose.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.compose.navigation.BottomNavConfiguration
import ru.alexgladkov.odyssey.compose.navigation.BottomNavModel

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