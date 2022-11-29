package ru.alexgladkov.odyssey_demo.theme

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import ru.alexgladkov.common.compose.theme.Odyssey
import ru.alexgladkov.common.compose.theme.OdysseyTheme
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.setupWithActivity
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.configuration.DefaultModalConfiguration

fun ComponentActivity.setupThemedNavigation(
    startScreen: String,
    vararg providers: ProvidedValue<*>,
    navigationGraph: RootComposeBuilder.() -> Unit
) {
    setContent {
        OdysseyTheme {
            val backgroundColor = Odyssey.color.primaryBackground
            val rootController = RootComposeBuilder()
                .apply(navigationGraph).build()
            rootController.backgroundColor = backgroundColor
            rootController.setupWithActivity(this)

            CompositionLocalProvider(
                *providers,
                LocalRootController provides rootController
            ) {
                ModalNavigator(configuration = DefaultModalConfiguration().copy(statusBarColor = backgroundColor)) {
                    Navigator(startScreen)
                }
            }
        }
    }
}