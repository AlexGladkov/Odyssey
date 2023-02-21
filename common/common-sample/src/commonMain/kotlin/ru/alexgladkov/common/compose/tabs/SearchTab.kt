package ru.alexgladkov.common.compose.tabs

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabItem

class SearchTab : TabItem() {

    override val configuration: TabConfiguration
        get() {
            return TabConfiguration(
                title = "Search",
                selectedColor = Color.White,
                unselectedColor = Color.White,
                titleStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
}