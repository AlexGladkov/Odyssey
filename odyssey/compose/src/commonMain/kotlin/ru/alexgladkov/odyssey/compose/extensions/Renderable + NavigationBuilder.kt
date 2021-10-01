package ru.alexgladkov.odyssey.compose.extensions

import androidx.compose.runtime.Composable
import ru.alexgladkov.common.compose.`compose-extension`.ComposeRenderable
import ru.alexgladkov.odyssey.core.NavigationFlowBuilder
import ru.alexgladkov.odyssey.core.NavigationScreen
import ru.alexgladkov.odyssey.core.NavigationStackEntry

fun NavigationFlowBuilder.destination(
    screen: NavigationScreen,
    content: @Composable NavigationStackEntry.() -> Unit
) {
    destination(
        screen = screen, content = ComposeRenderable(
            render = content
        )
    )
}