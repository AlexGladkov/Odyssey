package ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle

/**
 * Class for Bottom Navigation Tab Configuration
 */
abstract class TabItem {
    val name: String
        get() = this::class.simpleName!!

    abstract val configuration: TabConfiguration
        @Composable get
}

/**
 * Class for Bottom Navigation UI Configuration
 * @param title - tab title
 * @param selectedIcon - painter when tab selected
 * @param unselectedIcon - painter when tab not selected
 * @param selectedColor - color when tab selected (overrides bottom nav color)
 * @param unselectedColor - color when tab not selected (overrides bottom nav color)
 * @param titleStyle - text style for tab title
 */
data class TabConfiguration(
    val title: String = "",
    val selectedIcon: Painter? = null,
    val unselectedIcon: Painter? = null,
    val selectedColor: Color? = null,
    val unselectedColor: Color? = null,
    val titleStyle: TextStyle? = null
)