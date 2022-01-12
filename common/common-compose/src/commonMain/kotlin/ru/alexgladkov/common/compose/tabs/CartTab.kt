package ru.alexgladkov.common.compose.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.compose.navigation.bottom.TabConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom.TabItem

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