package ru.alexgladkov.odyssey.core.extensions

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import ru.alexgladkov.odyssey.core.base.Navigator
import ru.alexgladkov.odyssey.core.local.LocalRootController
import ru.alexgladkov.odyssey.core.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.core.navigation.bottom_sheet_navigation.ModalSheetNavigator

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
            ModalSheetNavigator {
                Navigator(startScreen)
            }
        }
    }
}