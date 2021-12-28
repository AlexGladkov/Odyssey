package ru.alexgladkov.odyssey.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle

abstract class TabItem {
    abstract val configuration: TabConfiguration
        @Composable get
}

data class TabConfiguration(
    val title: String = "",
    val icon: Painter? = null,
    val selectedColor: Color? = null,
    val unselectedColor: Color? = null,
    val titleStyle: TextStyle? = null
)