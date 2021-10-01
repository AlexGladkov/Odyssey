package ru.alexgladkov.odyssey.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import ru.alexgladkov.common.compose.`compose-extension`.ComposeRenderable
import ru.alexgladkov.common.compose.`compose-extension`.FlowComposeRenderable
import ru.alexgladkov.odyssey.core.NavigationFlowBuilder
import ru.alexgladkov.odyssey.core.NavigationScreen
import ru.alexgladkov.odyssey.core.NavigationStackEntry
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.compose.extensions.observeAsState

@Composable
fun RootHost(
    startScreen: NavigationScreen,
    rootController: RootController,
    flowBuilder: NavigationFlowBuilder.() -> Unit
) {
    val navigationFlow = remember { NavigationFlowBuilder(startScreen, rootController).apply(flowBuilder).build() }
    val currentDestination = rootController.destinationState.observeAsState()
    val destinationValue = currentDestination.value ?: NavigationStackEntry(startScreen, null)

    if (rootController.backStackEntry.isEmpty()) {
        rootController.launch(startScreen)
    } else {
        val contentValue = navigationFlow.destinations.firstOrNull { it.entry.screen == destinationValue.screen }
            ?: throw NotImplementedError("Can't find ${destinationValue.screen} in ${rootController.debugName} navigation graph.")

        when (contentValue.content) {
            is ComposeRenderable -> (contentValue.content as ComposeRenderable).render(destinationValue)
            is FlowComposeRenderable -> (contentValue.content as FlowComposeRenderable).render(rootController)
        }
    }
}