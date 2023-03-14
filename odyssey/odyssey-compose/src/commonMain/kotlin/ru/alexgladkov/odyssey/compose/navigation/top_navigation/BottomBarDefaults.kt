package ru.alexgladkov.odyssey.compose.navigation.top_navigation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object TopBarDefaults {

    @Composable
    fun colors(
        backgroundColor: Color = MaterialTheme.colors.primary,
        dividerColor: Color = MaterialTheme.colors.secondary,
        contentColor: Color = MaterialTheme.colors.onPrimary
    ): TopBarColors = DefaultTopBarColors(
        backgroundColor = backgroundColor,
        dividerColor = dividerColor,
        contentColor = contentColor
    )

    @Composable
    fun backgroundLineColors(
        backgroundColor: Color = MaterialTheme.colors.primary,
        contentColor: Color = MaterialTheme.colors.onPrimary
    ): TopBarColors = DefaultTopBarColors(
        backgroundColor = backgroundColor,
        dividerColor = backgroundColor,
        contentColor = contentColor
    )
}

@Stable
interface TopBarColors {
    /**
     * Represents the background color for this.
     */
    @Composable
    fun backgroundColor(): State<Color>

    /**
     * Represents the divider line color for this.
     */
    @Composable
    fun dividerColor(): State<Color>

    /**
     * Represents the content color (selected divider and text) for this.
     */
    @Composable
    fun contentColor(): State<Color>
}

@Immutable
private class DefaultTopBarColors(
    private val backgroundColor: Color,
    private val dividerColor: Color,
    private val contentColor: Color
) : TopBarColors {

    @Composable
    override fun backgroundColor(): State<Color> {
        return rememberUpdatedState(backgroundColor)
    }

    @Composable
    override fun dividerColor(): State<Color> {
        return rememberUpdatedState(dividerColor)
    }

    @Composable
    override fun contentColor(): State<Color> {
        return rememberUpdatedState(dividerColor)
    }
}