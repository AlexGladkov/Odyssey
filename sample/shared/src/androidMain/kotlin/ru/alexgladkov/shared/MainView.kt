package ru.alexgladkov.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ru.alexgladkov.odyssey.core.configuration.DisplayType
import ru.alexgladkov.shared.navigation.navigationGraph
import ru.alexgladkov.shared.theme.Odyssey
import ru.alexgladkov.shared.theme.OdysseyTheme

@Composable
fun MainView(platformConfiguration: PlatformConfiguration) {
    OdysseyTheme {
        val configuration = OdysseyConfiguration(
            canvas = platformConfiguration.activity,
            displayType = DisplayType.EdgeToEdge,
            backgroundColor = Odyssey.color.primaryBackground,
            navigationBarColor = Odyssey.color.primaryBackground.toArgb(),
            breadcrumbCallback = { breadcrumb ->

            }
        )

        setNavigationContent(configuration) {
            navigationGraph()
        }
    }
}