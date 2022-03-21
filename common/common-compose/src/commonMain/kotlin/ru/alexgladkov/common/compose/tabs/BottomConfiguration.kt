package ru.alexgladkov.common.compose.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.BottomNavConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabsNavModel
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TopNavConfiguration

class BottomConfiguration : TabsNavModel<BottomNavConfiguration>() {

    override val navConfiguration: BottomNavConfiguration
        @Composable
        get() {
            return BottomNavConfiguration(
                backgroundColor = Color.White,
                selectedColor = Color.DarkGray,
                unselectedColor = Color.LightGray
            )
        }
}

class TopConfiguration : TabsNavModel<TopNavConfiguration>() {

    override val navConfiguration: TopNavConfiguration
        @Composable
        get() {
            return TopNavConfiguration(
                backgroundColor = Color.White,
                contentColor = Color.DarkGray
            )
        }
}