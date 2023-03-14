package ru.alexgladkov.odyssey.compose.setup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.configuration.DefaultModalConfiguration
import ru.alexgladkov.odyssey.core.configuration.DisplayType

@Composable
actual fun setNavigationContent(configuration: OdysseyConfiguration, onApplicationFinish: () -> Unit, navigationGraph: @Composable RootComposeBuilder.() -> Unit) {
    val builder = RootComposeBuilder().apply { navigationGraph.invoke(this) }
    val rootController = remember { builder.build() }
    rootController.backgroundColor = configuration.backgroundColor
    rootController.onApplicationFinish = onApplicationFinish

    CompositionLocalProvider(
        LocalRootController provides rootController
    ) {
        ModalNavigator(configuration = DefaultModalConfiguration(backgroundColor = configuration.backgroundColor, DisplayType.EdgeToEdge)) {
            when (val startScreen = configuration.startScreen) {
                is StartScreen.Custom -> Navigator(startScreen = startScreen.startName)
                StartScreen.First -> Navigator(startScreen = rootController.getFirstScreenName())
            }
        }
    }
}