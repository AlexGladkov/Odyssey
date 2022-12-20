package ru.alexgladkov.odyssey.compose.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.helpers.BottomSheetBundle
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.CoreRootController
import ru.alexgladkov.odyssey.core.NavConfiguration
import ru.alexgladkov.odyssey.core.screen.ScreenInteractor
import ru.alexgladkov.odyssey.core.toScreenBundle

@Composable
fun Navigator(
    modifier: Modifier = Modifier,
    startScreen: String? = null,
    startParams: Any? = null
) {
    val rootController = LocalRootController.current
    val backgroundColor = rootController.backgroundColor
    val navConfiguration = rootController.currentScreen.collectAsState()

    navConfiguration.value?.let { config ->
        NavigatorAnimated(
            config.screen,
            config,
            rootController,
            backgroundColor,
            modifier
        )
    }

    LaunchedEffect(Unit) {
        rootController.drawCurrentScreen(startScreen = startScreen, startParams = startParams)
    }
}

@Composable
private fun NavigatorAnimated(
    screen: ScreenInteractor,
    configuration: NavConfiguration,
    rootController: RootController,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    AnimatedHost(
        currentScreen = screen.toScreenBundle(),
        screenToRemove = configuration.screenToRemove?.toScreenBundle(),
        animationType = screen.animationType,
        isForward = screen.isForward,
        modifier = modifier
            .background(backgroundColor)
            .fillMaxSize(),
        onScreenRemove = rootController.onScreenRemove
    ) { currentScreen ->
        val renderKey = when {
            currentScreen.realKey?.contains(CoreRootController.multiStackKey) == true -> CoreRootController.multiStackKey
            currentScreen.realKey?.contains(CoreRootController.flowKey) == true -> CoreRootController.flowKey
            else -> currentScreen.realKey
        }
        val render = rootController.getScreenRender(renderKey)
        render?.invoke(currentScreen.params)
            ?: throw IllegalStateException("Screen $currentScreen not found in screenMap")
    }
}

@Composable
fun NavigatorModalSheet(
    bundle: BottomSheetBundle,
    rootController: RootController
) {
    val presentedScreen = rootController.getScreenRender(bundle.currentKey)
    val modalSheet = rootController.getScreenRender(bundle.key)

    Box(modifier = Modifier.fillMaxSize()) {
        presentedScreen?.invoke(null)
        modalSheet?.invoke(null)
    }
}