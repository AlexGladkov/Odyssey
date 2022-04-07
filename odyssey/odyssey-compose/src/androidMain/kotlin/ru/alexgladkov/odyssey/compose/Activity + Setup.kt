package ru.alexgladkov.odyssey.compose

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.setupWithActivity
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator

fun ComponentActivity.setupNavigation(
    startScreen: String,
    vararg providers: ProvidedValue<*>,
    navigationGraph: RootComposeBuilder.() -> Unit
) {
    val rootController = RootComposeBuilder().apply(navigationGraph).build()
    rootController.setupWithActivity(this)

    setContent {
        CompositionLocalProvider(
            *providers,
            LocalRootController provides rootController
        ) {
            ModalNavigator {
                Navigator(startScreen)
            }
        }
    }
}