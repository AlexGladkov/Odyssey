package ru.alexgladkov.odyssey.compose.navigation.tabs

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle

/**
 * Class for Bottom Navigation Tab Configuration
 */
interface TabItem {
    val name: String
        get() = this::class.simpleName!!
}

/**
 * Class for Bottom Navigation UI Configuration
 * @param title - tab title
 * @param icon - painter for icon
 * @param selectedIconColor - color for icon's selected state
 * @param unselectedIconColor - color for icon's unselected state
 * @param selectedTextColor - color when tab selected
 * @param unselectedTextColor - color when tab not selected
 * @param titleStyle - text style for tab title
 */
data class TabConfiguration(
    val title: String,
    val icon: Painter? = null,
    val selectedIconColor: Color,
    val unselectedIconColor: Color,
    val selectedTextColor: Color,
    val unselectedTextColor: Color,
    val titleStyle: TextStyle? = null
)