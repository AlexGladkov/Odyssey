package ru.alexgladkov.shared

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp
import ru.alexgladkov.odyssey.compose.base.local.BottomConfiguration
import ru.alexgladkov.odyssey.compose.base.local.LocalBottomConfiguration
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ru.alexgladkov.shared.PlatformConfiguration
import ru.alexgladkov.shared.navigation.navigationGraph
import ru.alexgladkov.shared.theme.Odyssey
import ru.alexgladkov.shared.theme.OdysseyTheme

@Composable
internal fun MainView(platformConfiguration: PlatformConfiguration) {
    OdysseyTheme {
        CompositionLocalProvider(
            LocalBottomConfiguration provides BottomConfiguration(
                backgroundColor = Odyssey.color.primaryBackground,
                selectedColor = Odyssey.color.tintColor,
                unselectedColor = Odyssey.color.controlColor,
                elevation = 8.dp
            )
        ) {
            val configuration = OdysseyConfiguration(
                backgroundColor = Odyssey.color.primaryBackground
            )

            setNavigationContent(configuration) {
                navigationGraph()
            }
        }
    }
}