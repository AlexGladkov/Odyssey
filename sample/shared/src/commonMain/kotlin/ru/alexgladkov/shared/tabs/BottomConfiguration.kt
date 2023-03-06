package ru.alexgladkov.shared.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import ru.alexgladkov.shared.theme.Odyssey
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.*

class BottomConfiguration : TabsNavModel<BottomNavConfiguration>() {

    override val navConfiguration: BottomNavConfiguration
        @Composable
        get() {
            return BottomNavConfiguration(
                backgroundColor = Odyssey.color.secondaryBackground,
                selectedColor = Odyssey.color.primaryText,
                unselectedColor = Odyssey.color.controlColor,
                elevation = 0.dp
            )
        }
}

class TopConfiguration : TabsNavModel<TopNavConfiguration>() {

    override val navConfiguration: TopNavConfiguration
        @Composable
        get() {
            return TopNavConfiguration(
                backgroundColor = Odyssey.color.secondaryBackground,
                contentColor = Odyssey.color.primaryText
            )
        }
}

class CustomConfiguration(private val content: @Composable (params: Any?) -> Unit) :
    TabsNavModel<CustomNavConfiguration>() {

    override val navConfiguration: CustomNavConfiguration
        @Composable
        get() {
            return CustomNavConfiguration(
               content = content
            )
        }
}
