package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.runtime.Composable
import ru.alexgladkov.odyssey.compose.extensions.observeAsState
import ru.alexgladkov.odyssey.core.controllers.FlowRootController
import ru.alexgladkov.odyssey.core.destination.DestinationScreen

@Composable
fun FlowHost(screenBundle: ScreenBundle) {
    val flowRootController = screenBundle.rootController as FlowRootController
    val navigation = flowRootController.backStackObserver.observeAsState()

    // This ugly hack needs to prevent double display for previous flow state.
    // Fixme: need to fix it for navigation optimization
    if (flowRootController.allowedDestinations.map { it.destinationName() }
            .contains(navigation.value?.destination?.destinationName().orEmpty())) {
        val params = (navigation.value?.destination as? DestinationScreen)?.params
        navigation.value?.let { entry ->
            val render = screenBundle.screenMap[entry.destination.destinationName()]
            render?.invoke(
                screenBundle.copy(params = if (flowRootController.backStack.size == 1) screenBundle.params else params)
            )
        }
    }
}