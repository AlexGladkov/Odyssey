package ru.alexgladkov.odyssey.compose.extensions

import androidx.compose.runtime.Composable
import ru.alexgladkov.odyssey.compose.helpers.*
import ru.alexgladkov.odyssey.compose.navigation.BottomNavModel
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.screen(
    name: String,
    content: @Composable (Any?) -> Unit
) {
    addScreen(
        key = name,
        screenMap = hashMapOf(name to content)
    )
}

fun RootComposeBuilder.flow(
    name: String,
    builder: FlowBuilder.() -> Unit
) {
    addFlow(
        key = name,
        flowBuilderModel = FlowBuilder(name).apply(builder).build()
    )
}

fun RootComposeBuilder.bottomNavigation(
    name: String,
    bottomNavModel: BottomNavModel,
    builder: MultiStackBuilder.() -> Unit
) {
    addMultiStack(
        key = name,
        bottomNavModel = bottomNavModel,
        multiStackBuilderModel = MultiStackBuilder(name).apply(builder).build()
    )
}