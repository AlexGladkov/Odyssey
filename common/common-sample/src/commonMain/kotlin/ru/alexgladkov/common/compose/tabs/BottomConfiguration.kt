package ru.alexgladkov.common.compose.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.alexgladkov.common.compose.theme.Odyssey
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.*

class BottomConfiguration : TabsNavModel<BottomNavConfiguration>() {

    override val navConfiguration: BottomNavConfiguration
        get() {
            return BottomNavConfiguration(
                backgroundColor = Color.White,
                selectedColor = Color.White,
                unselectedColor = Color.White,
                elevation = 0.dp
            )
        }
}

class TopConfiguration : TabsNavModel<TopNavConfiguration>() {

    override val navConfiguration: TopNavConfiguration
        get() {
            return TopNavConfiguration(
                backgroundColor = Color.White,
                contentColor = Color.White
            )
        }
}

class CustomConfiguration(private val content: @Composable (params: Any?) -> Unit) :
    TabsNavModel<CustomNavConfiguration>() {

    override val navConfiguration: CustomNavConfiguration
        get() {
            return CustomNavConfiguration(
               content = content
            )
        }
}
