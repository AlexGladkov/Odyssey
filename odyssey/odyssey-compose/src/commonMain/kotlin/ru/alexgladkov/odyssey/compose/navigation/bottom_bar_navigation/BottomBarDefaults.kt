package ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object BottomBarDefaults {

    @Composable
    fun bottomColors(
        backgroundColor: Color = MaterialTheme.colors.primary,
    ): BottomBarColors = DefaultBottomBarColors(
        backgroundColor = backgroundColor
    )

    @Composable
    fun elevation(
        defaultElevation: Dp = 8.dp
    ): BottomBarElevations = DefaultElevations(
        defaultElevation = defaultElevation
    )
}

@Stable
interface BottomBarColors {
    /**
     * Represents the background color for this button.
     */
    @Composable
    fun backgroundColor(): State<Color>
}

@Stable
interface BottomBarElevations {

    @Composable
    fun default(): State<Dp>
}

@Immutable
private class DefaultBottomBarColors(
    private val backgroundColor: Color
) : BottomBarColors {

    @Composable
    override fun backgroundColor(): State<Color> {
        return rememberUpdatedState(backgroundColor)
    }
}

@Immutable
private class DefaultElevations(
    private val defaultElevation: Dp
): BottomBarElevations {

    @Composable
    override fun default(): State<Dp> {
        return rememberUpdatedState(defaultElevation)
    }
}