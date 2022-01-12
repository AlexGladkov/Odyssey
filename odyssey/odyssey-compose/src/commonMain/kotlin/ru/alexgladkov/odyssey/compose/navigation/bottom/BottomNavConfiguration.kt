package ru.alexgladkov.odyssey.compose.navigation.bottom

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Abstract class needed to configure bottom bar navigation
 */
abstract class BottomNavModel {
    /**
     * Contains base UI configuration for bottom bar navigation
     */
    abstract val bottomNavConfiguration: BottomNavConfiguration
        @Composable get

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
 */
data class BottomNavConfiguration(
    val backgroundColor: Color,
    val selectedColor: Color,
    val unselectedColor: Color
)