package ru.alexgladkov.odyssey.compose.setup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.setupWithActivity
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
    rootController.setupWithActivity(configuration.canvas)
    rootController.onApplicationFinish = onApplicationFinish
    rootController.onScreenNavigate = configuration.breadcrumbCallback

    when (configuration.displayType) {
        is DisplayType.FullScreen -> {
            WindowCompat.setDecorFitsSystemWindows(configuration.canvas.window, false)
            configuration.canvas.window.statusBarColor = configuration.statusBarColor
            configuration.canvas.window.navigationBarColor = configuration.navigationBarColor
        }
        else -> {}
    }

    CompositionLocalProvider(
        LocalRootController provides rootController
    ) {
        ModalNavigator(configuration = DefaultModalConfiguration(configuration.backgroundColor, configuration.displayType)) {
            when (val startScreen = configuration.startScreen) {
                is StartScreen.Custom -> Navigator(startScreen = startScreen.startName)
                StartScreen.First -> Navigator(startScreen = rootController.getFirstScreenName())
            }
        }
    }
}