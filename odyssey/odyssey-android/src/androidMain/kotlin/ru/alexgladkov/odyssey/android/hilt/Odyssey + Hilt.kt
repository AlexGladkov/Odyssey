package ru.alexgladkov.odyssey.android.hilt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import ru.alexgladkov.odyssey.android.hilt.local.LocalHiltViewModelStoreOwnerManager
import ru.alexgladkov.odyssey.android.hilt.viewmodel.HiltViewModelStoreOwnerManager
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.setupWithActivity
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.configuration.DefaultModalConfiguration
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.StartScreen
import ru.alexgladkov.odyssey.core.configuration.DisplayType

@Composable
fun setHiltNavigation(configuration: OdysseyConfiguration, onApplicationFinish: () -> Unit = {}, navigationGraph: RootComposeBuilder.() -> Unit) {
    val rootController = RootComposeBuilder().apply(navigationGraph).build()
    rootController.backgroundColor = configuration.backgroundColor
    rootController.setupWithActivity(configuration.canvas)
    rootController.onApplicationFinish = onApplicationFinish

    val storeOwnerManager = HiltViewModelStoreOwnerManager(
        context = configuration.canvas,
        rootController = rootController
    )

    when (configuration.displayType) {
        is DisplayType.FullScreen -> {
            WindowCompat.setDecorFitsSystemWindows(configuration.canvas.window, false)
            configuration.canvas.window.statusBarColor = configuration.statusBarColor
            configuration.canvas.window.navigationBarColor = configuration.navigationBarColor
        }
        else -> {}
    }

    CompositionLocalProvider(
        LocalHiltViewModelStoreOwnerManager provides storeOwnerManager,
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