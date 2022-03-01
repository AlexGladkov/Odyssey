package ru.alexgladkov.odyssey.core.extensions

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.backpress.OnBackPressedDispatcher

fun RootController.setupWithActivity(activity: ComponentActivity) {
    val dispatcher = activity.onBackPressedDispatcher
    val rootDispatcher = OnBackPressedDispatcher()
    dispatcher.addCallback(object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            rootDispatcher.onBackPressed()
        }
    })

    onApplicationFinish = {
        activity.finish()
    }
    setupBackPressedDispatcher(rootDispatcher)
}