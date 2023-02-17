package ru.alexgladkov.odyssey.compose.extensions

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.core.view.WindowCompat
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.core.backpress.OnBackPressedDispatcher
import ru.alexgladkov.odyssey.core.configuration.DisplayType

fun RootController.setupWithActivity(activity: ComponentActivity) {
    setDeepLinkUri(activity.intent?.data?.path)

    val dispatcher = activity.onBackPressedDispatcher
    val rootDispatcher = OnBackPressedDispatcher()
    dispatcher.addCallback(object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            rootDispatcher.onBackPressed()
        }
    })

    setupBackPressedDispatcher(rootDispatcher)
}