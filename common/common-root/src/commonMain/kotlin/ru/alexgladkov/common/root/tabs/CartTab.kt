package ru.alexgladkov.common.root.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.core.navigation.bottom_bar_navigation.TabConfiguration
import ru.alexgladkov.odyssey.core.navigation.bottom_bar_navigation.TabItem

class CartTab : TabItem() {

    override val configuration: TabConfiguration
        @Composable
        get() {
            return TabConfiguration(
                title = "Cart",
                selectedColor = Color.DarkGray,
                unselectedColor = Color.LightGray
            )
        }
}