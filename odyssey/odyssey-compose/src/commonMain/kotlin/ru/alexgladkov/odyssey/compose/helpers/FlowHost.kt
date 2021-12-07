package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.animation.*
import androidx.compose.runtime.*
import ru.alexgladkov.odyssey.compose.animations.AnimatedTransition
import ru.alexgladkov.odyssey.compose.extensions.launchAsState
import ru.alexgladkov.odyssey.compose.extensions.observeAsState
import ru.alexgladkov.odyssey.core.NavigationEntry
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.controllers.FlowRootController
import ru.alexgladkov.odyssey.core.destination.DestinationScreen

@OptIn(ExperimentalAnimationApi::class)
/**
 * Flow host
 * Use this for switch between screens inside flow root controller
 *
 * @param screenBundle - params, rootcontroller, etc
 */
@Composable
fun FlowHost(screenBundle: ScreenBundle) {
    val flowRootController = screenBundle.rootController as FlowRootController
    val navigation = flowRootController.backStackObserver.observeAsState(flowRootController.backStack.last())
    val params = (navigation.value?.destination as? DestinationScreen)?.params
    var currentStackCount by remember { mutableStateOf(flowRootController.backStack.size) }
    val destinationStackCount = flowRootController.backStack.size

    navigation.value?.let { entry ->
        AnimatedTransition(
            targetState = entry,
            animation = entry.animationType,
            isForwardDirection = destinationStackCount > currentStackCount,
            onAnimationEnd = {
                currentStackCount = destinationStackCount
            }
        ) {
            val render = screenBundle.screenMap[destination.destinationName()]
            render?.invoke(
                screenBundle.copy(params = if (flowRootController.backStack.size == 1) screenBundle.params else params)
            )
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
fun TabHost(
    navigationEntry: NavigationEntry,
    screenBundle: ScreenBundle,
) {
    val flowRootController = navigationEntry.rootController as FlowRootController
    var currentStackCount by remember { mutableStateOf(flowRootController.backStack.size) }
    val destinationStackCount = flowRootController.backStack.size
    val navigation = flowRootController.backStackObserver
        .launchAsState(key = navigationEntry, initial = flowRootController.backStack.last())
    var currentRootController by remember { mutableStateOf(navigationEntry.rootController.debugName) }
    val isTabSwitched = currentRootController != navigationEntry.rootController.debugName

    navigation?.let {
        AnimatedTransition(
            targetState = navigation,
            animation = if (isTabSwitched) AnimationType.None else navigation.animationType,
            isForwardDirection = destinationStackCount > currentStackCount,
            onAnimationEnd = {
                currentRootController = navigationEntry.rootController.debugName.orEmpty()
                currentStackCount = destinationStackCount
            }
        ) {
            val params = (destination as? DestinationScreen)?.params
            val render = screenBundle.screenMap[destination.destinationName()]
            render?.invoke(
                screenBundle.copy(params = params)
            )
        }
    }
}

private fun resolveAnimationType(
    defaultAnimationType: AnimationType,
    launchAnimationType: AnimationType
): AnimationType {
    return if (defaultAnimationType == launchAnimationType) {
        defaultAnimationType
    } else {
        if (launchAnimationType !is AnimationType.None) {
            launchAnimationType
        } else {
            defaultAnimationType
        }
    }
}