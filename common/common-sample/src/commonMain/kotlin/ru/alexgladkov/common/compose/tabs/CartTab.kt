package ru.alexgladkov.common.compose.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.common.compose.theme.Odyssey
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabItem

class CartTab : TabItem() {

    override val configuration: TabConfiguration
        get() {
            return TabConfiguration(
                title = "Cart",
                selectedColor = Color.White,
                unselectedColor = Color.White,
            )
        }
}