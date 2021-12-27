package ru.alexgladkov.odyssey.compose.base

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.NavConfiguration
import ru.alexgladkov.odyssey.core.animations.defaultFadeAnimation
import ru.alexgladkov.odyssey.core.animations.defaultPresentationAnimation
import ru.alexgladkov.odyssey.core.extensions.Closeable
import ru.alexgladkov.odyssey.core.screen.Screen
import ru.alexgladkov.odyssey.core.screen.ScreenBundle
import ru.alexgladkov.odyssey.core.screen.ScreenInteractor
import ru.alexgladkov.odyssey.core.wrap

@Composable
fun Navigator(startScreen: String = "") {
    val rootController = LocalRootController.current
    var navConfiguration: NavConfiguration by remember {
        mutableStateOf(
            Screen(
                key = startScreen,
                animationType = if (startScreen.isEmpty()) defaultFadeAnimation() else defaultPresentationAnimation()
            ).wrap()
        )
    }
    val screen = navConfiguration.screen
    var closeable: Closeable? = null

    AnimatedHost(
        currentScreen = ScreenBundle(key = screen.key, params = screen.params),
        removeScreen = navConfiguration.removeScreen,
        animationType = screen.animationType,
        isForward = screen.isForward,
        modifier = Modifier.fillMaxSize()
    ) { currentScreen ->
        val render = rootController.screenMap[currentScreen.key]
        render?.invoke(currentScreen.params)
            ?: throw IllegalStateException("Screen $currentScreen not found in screenMap")
    }

    LaunchedEffect(Unit) {
        closeable = rootController.currentScreen.watch {
            navConfiguration = it
        }

        rootController.launch(startScreen)
    }

    DisposableEffect(Unit) {
        onDispose {
            closeable?.close()
        }
    }
}