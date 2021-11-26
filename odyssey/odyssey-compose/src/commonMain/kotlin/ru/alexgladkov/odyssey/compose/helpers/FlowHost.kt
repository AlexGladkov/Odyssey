package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.animation.*
import androidx.compose.runtime.*
import ru.alexgladkov.odyssey.compose.animations.AnimatedPush
import ru.alexgladkov.odyssey.compose.extensions.launchAsState
import ru.alexgladkov.odyssey.compose.extensions.observeAsState
import ru.alexgladkov.odyssey.core.NavigationEntry
import ru.alexgladkov.odyssey.core.controllers.FlowRootController
import ru.alexgladkov.odyssey.core.destination.DestinationScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FlowHost(screenBundle: ScreenBundle) {
    val flowRootController = screenBundle.rootController as FlowRootController
    val navigation by flowRootController.backStackObserver.observeAsState()
    var currentStackCount by remember { mutableStateOf(flowRootController.backStack.size) }
    val destinationStackCount = flowRootController.backStack.size

    // This ugly hack needs to prevent double display for previous flow state.
    // Fixme: need to fix it for navigation optimization
    if (flowRootController.allowedDestinations.map { it.destinationName() }
            .contains(navigation?.destination?.destinationName().orEmpty())) {
        val params = (navigation?.destination as? DestinationScreen)?.params

        AnimatedPush(
            targetState = navigation,
            isForward = destinationStackCount > currentStackCount,
            transitionTime = 300,
            onAnimationEnd = { currentStackCount = destinationStackCount }
        ) {
            this?.destination?.destinationName()?.let {
                val render = screenBundle.screenMap[it]
                render?.invoke(
                    screenBundle.copy(
                        params = if (flowRootController.backStack.size == 1)
                            screenBundle.params
                        else
                            params
                    )
                )
            }
        }
    }
}

/**
 * Tab host
 * Use this for switch between screens inside tab for multistack root controller
 *
 * @param navigationEntry - out navigation describes tab info (rc, params, etc)
 * @param screenBundle - params, rootcontroller, etc
 */
@Composable
fun TabHost(navigationEntry: NavigationEntry?, screenBundle: ScreenBundle) {
    val flowRootController = screenBundle.rootController as FlowRootController
    val previousRootController = remember { mutableStateOf(screenBundle.rootController) }
    var currentStackCount by remember { mutableStateOf(flowRootController.backStack.size) }
    val destinationStackCount = flowRootController.backStack.size
    val navigation = flowRootController.backStackObserver
        .launchAsState(key = navigationEntry, initial = flowRootController.backStack.last())

    // Check if we switch tabs (root controller changed)
    if (flowRootController.debugName == previousRootController.value.debugName) {
        AnimatedPush(
            targetState = navigation,
            isForward = destinationStackCount > currentStackCount,
            transitionTime = 300,
            onAnimationEnd = { currentStackCount = destinationStackCount }
        ) {
            val params = (this?.destination as? DestinationScreen)?.params
            this?.let { entry ->
                val render = screenBundle.screenMap[entry.destination.destinationName()]
                render?.invoke(
                    screenBundle.copy(params = params)
                )
            }
        }
    } else {
        previousRootController.value = flowRootController
        val params = (navigation?.destination as? DestinationScreen)?.params
        navigation?.let { entry ->
            val render = screenBundle.screenMap[entry.destination.destinationName()]
            render?.invoke(
                screenBundle.copy(params = params)
            )
        }
    }
}