package ru.alexgladkov.odyssey.compose.extensions

import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

//fun ComposableScreenHost.setupWithRootController(startScreen: String, block: RootComposeBuilder.() -> Unit) {
//    prepareFowDrawing()
//
//    val builder = RootComposeBuilder(screenHost = this)
//    val rootController = builder.apply(block).build()
//
//    setScreenMap(builder.screenMap)
//    rootController.launch(screen = startScreen)
//}