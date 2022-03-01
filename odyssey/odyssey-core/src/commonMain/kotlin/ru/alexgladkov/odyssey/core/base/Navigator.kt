package ru.alexgladkov.odyssey.core.base

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.collect
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.NavConfiguration
import ru.alexgladkov.odyssey.core.extensions.Closeable
import ru.alexgladkov.odyssey.core.local.LocalRootController
import ru.alexgladkov.odyssey.core.screen.ScreenBundle
import ru.alexgladkov.odyssey.core.screen.ScreenInteractor
import ru.alexgladkov.odyssey.core.toScreenBundle

@Composable
fun Navigator(startParams: Any? = null) {
    val rootController = LocalRootController.current
    val navConfiguration = rootController.currentScreen.collectAsState()

    navConfiguration.value.let { config ->
        NavigatorAnimated(config.screen, config, rootController)
    }

    LaunchedEffect(Unit) {
        rootController.drawCurrentScreen(startParams)
    }
}

@Composable
private fun NavigatorAnimated(
    screen: ScreenInteractor,
    configuration: NavConfiguration,
    rootController: RootController
) {
    AnimatedHost(
        currentScreen = screen.toScreenBundle(),
        screenToRemove = configuration.screenToRemove?.toScreenBundle(),
        animationType = screen.animationType,
        isForward = screen.isForward,
        modifier = Modifier.fillMaxSize(),
        onScreenRemove = rootController.onScreenRemove
    ) { currentScreen ->
        val render = rootController.screenMap[currentScreen.realKey]
        render?.invoke(currentScreen.params)
            ?: throw IllegalStateException("Screen $currentScreen not found in screenMap")
    }
}