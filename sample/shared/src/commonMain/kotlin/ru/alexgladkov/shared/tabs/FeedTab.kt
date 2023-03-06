package ru.alexgladkov.shared.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.alexgladkov.shared.theme.Odyssey
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabItem

class FeedTab : TabItem() {

    override val configuration: TabConfiguration
        @Composable
        get() {
            return TabConfiguration(
                title = "Feed",
                selectedColor = Odyssey.color.primaryText,
                unselectedColor = Odyssey.color.controlColor,
                titleStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
}