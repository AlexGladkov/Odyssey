package ru.alexgladkov.`odyssey-demo`

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ru.alexgladkov.common.compose.navigation.navigationGraph
import ru.alexgladkov.common.compose.theme.LocalTheme
import ru.alexgladkov.common.compose.theme.Odyssey
import ru.alexgladkov.common.compose.theme.OdysseyTheme
import ru.alexgladkov.common.compose.theme.ThemeEventBus
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Odyssey Desktop Example",
        state = rememberWindowState(
            width = 1024.dp,
            height = 800.dp,
            position = WindowPosition.Aligned(Alignment.Center)
        )
    ) {
        val themeEventBus = remember { ThemeEventBus() }
        val themeState = themeEventBus.themeState.collectAsState()

        OdysseyTheme(isDarkTheme = themeState.value.isDarkTheme) {
            val configuration = OdysseyConfiguration(
                backgroundColor = Odyssey.color.primaryBackground
            )

            CompositionLocalProvider(
                LocalTheme provides themeEventBus
            ) {
                setNavigationContent(configuration, onApplicationFinish = {
                    exitApplication()
                }) {
                    navigationGraph()
                }
            }
        }
    }
}