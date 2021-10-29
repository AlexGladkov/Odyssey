package ru.alexgladkov.odyssey.compose

import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.core.extensions.setupWithActivity

fun AndroidScreenHost.setupActivityWithRootController(startScreen: String, block: RootComposeBuilder.() -> Unit) {
    prepareFowDrawing()

    val builder = RootComposeBuilder(screenHost = this)
    val rootController = builder.apply(block).build()
    rootController.setupWithActivity(composeActivity)

    setScreenMap(builder.screenMap)
    rootController.launch(screen = startScreen)
}