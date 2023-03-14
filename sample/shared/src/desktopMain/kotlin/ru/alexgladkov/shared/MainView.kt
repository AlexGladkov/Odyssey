package ru.alexgladkov.shared

import androidx.compose.runtime.Composable
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ru.alexgladkov.shared.navigation.navigationGraph
import ru.alexgladkov.shared.theme.Odyssey
import ru.alexgladkov.shared.theme.OdysseyTheme

@Composable
fun MainView(onExitApplication: () -> Unit) {
    OdysseyTheme {
        val configuration = OdysseyConfiguration(
            backgroundColor = Odyssey.color.primaryBackground
        )

        setNavigationContent(configuration, onApplicationFinish = {
            onExitApplication()
        }) {
            navigationGraph()
        }
    }
}