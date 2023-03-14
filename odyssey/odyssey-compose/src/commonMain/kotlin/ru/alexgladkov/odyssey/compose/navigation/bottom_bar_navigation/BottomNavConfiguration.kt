package ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp


sealed interface MultiStackConfiguration {
    val type: TabsNavType

    /**
     * Base UI bottom bar configuration
     * @param backgroundColor - bar background color
     * @param defaultElevation - shadow control for bottom navigation
     */
    data class BottomNavConfiguration(
        val backgroundColor: Color,
        val defaultElevation: Dp,
    ) : MultiStackConfiguration {
        override val type = TabsNavType.Bottom
    }

    /**
     * Base UI top bar configuration
     * @param backgroundColor - bar background color
     * @param selectedColor - tabs content color
     */
    data class TopNavConfiguration(
        val backgroundColor: Color,
        val dividerColor: Color,
        val contentColor: Color
    ) : MultiStackConfiguration {
        override val type = TabsNavType.Top
    }

    /**
     * Base UI custom configuration
     * @param content - drawable content
     */
    data class CustomNavConfiguration(
        val content: @Composable (params: Any?) -> Unit
    ) : MultiStackConfiguration {
        override val type = TabsNavType.Custom
    }
}

enum class TabsNavType {
    Bottom, Top, Custom
}