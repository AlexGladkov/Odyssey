package ru.alexgladkov.odyssey.compose.navigation.tabs

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter

object TabDefaults {

    @Composable
    fun tabColors(
        selectedTextColor: Color = MaterialTheme.colors.primary,
        unselectedTextColor: Color = MaterialTheme.colors.secondary,
        selectedIconColor: Color = MaterialTheme.colors.primary,
        unselectedIconColor: Color = MaterialTheme.colors.secondary
    ): TabColors = DefaultTabColors(
        selectedTextColor = selectedTextColor,
        unselectedTextColor = unselectedTextColor,
        selectedIconColor = selectedIconColor,
        unselectedIconColor = unselectedIconColor
    )

    @Composable
    fun simpleTabColors(
        selectedColor: Color = MaterialTheme.colors.primary,
        unselectedColor: Color = MaterialTheme.colors.secondary
    ): TabColors = DefaultTabColors(
        selectedTextColor = selectedColor,
        unselectedTextColor = unselectedColor,
        selectedIconColor = selectedColor,
        unselectedIconColor = unselectedColor
    )

    @Composable
    fun content(
        title: String,
        icon: Painter? = null
    ): TabContent = DefaultTabContent(
        title = title,
        icon = icon
    )

    @Composable
    fun content(
        title: String,
        vector: ImageVector? = null
    ): TabContent = DefaultTabContent(
        title = title,
        icon = if (vector == null) null else rememberVectorPainter(vector)
    )
}

@Stable
interface TabColors {
    /**
     * Represents the text color for this tab depends on selected state.
     */
    @Composable
    fun textColor(selected: Boolean): State<Color>

    /**
     * Represents the icon color for this tab depends on selected state.
     */
    @Composable
    fun iconColor(selected: Boolean): State<Color>
}

@Stable
interface TabContent {
    /**
     * Represents title value for tab item.
     */
    @Composable
    fun title(): State<String>

    /**
     * Represents icon value for tab item.
     */
    @Composable
    fun icon(): State<Painter?>
}

/**
 * Default colors for every tab class
 * @param selectedTextColor - color for selected state of text
 * @param unselectedTextColor - color for unselected state of text
 * @param selectedIconColor - color for selected state of icon
 * @param unselectedIconColor - color for unselected state of icon
 */
@Immutable
private class DefaultTabColors(
    private val selectedTextColor: Color,
    private val unselectedTextColor: Color,
    private val selectedIconColor: Color,
    private val unselectedIconColor: Color
) : TabColors {

    @Composable
    override fun textColor(selected: Boolean): State<Color> {
        return rememberUpdatedState(if (selected) selectedTextColor else unselectedTextColor)
    }

    @Composable
    override fun iconColor(selected: Boolean): State<Color> {
        return rememberUpdatedState(if (selected) selectedIconColor else unselectedIconColor)
    }
}

/**
 * Default content for every tab class
 * @param title - displayed name
 * @param icon - dispalyed icon
 */
@Immutable
private class DefaultTabContent(
    private val title: String,
    private val icon: Painter?
) : TabContent {

    @Composable
    override fun title(): State<String> {
        return rememberUpdatedState(title)
    }

    @Composable
    override fun icon(): State<Painter?> {
        return rememberUpdatedState(icon)
    }
}
