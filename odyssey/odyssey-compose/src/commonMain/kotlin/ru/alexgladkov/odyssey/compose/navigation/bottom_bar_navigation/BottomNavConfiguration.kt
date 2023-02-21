package ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Abstract class needed to configure bottom bar navigation
 */
abstract class TabsNavModel<Cfg : TabsNavConfiguration> {
    /**
     * Contains base UI configuration for bottom bar navigation
     */
    abstract val navConfiguration: Cfg

    /**
     * Starts when bottom bar shows on the screen
     * Launches every recomposition, need to send some analytics
     */
    open val launchedEffect: @Composable () -> Unit = { }
}

/**
 * Base UI bottom bar configuration
 * @param backgroundColor - bar background color
 * @param selectedColor - tint color for selected tab
 * @param unselectedColor - tint color for unselected tab
 * @param elevation - shadow control for bottom navigation
 */
data class BottomNavConfiguration(
    val backgroundColor: Color,
    val selectedColor: Color,
    val unselectedColor: Color,
    val elevation: Dp = 8.dp,
) : TabsNavConfiguration {
    override val type = TabsNavType.Bottom
}

/**
 * Base UI top bar configuration
 * @param backgroundColor - bar background color
 * @param selectedColor - tabs content color
 */
data class TopNavConfiguration(
    val backgroundColor: Color,
    val contentColor: Color
) : TabsNavConfiguration {
    override val type = TabsNavType.Top
}

data class CustomNavConfiguration(
    val content: @Composable (params: Any?) -> Unit
) : TabsNavConfiguration {
    override val type = TabsNavType.Custom
}

enum class TabsNavType {
    Bottom, Top, Custom
}

interface TabsNavConfiguration {
    val type: TabsNavType
}