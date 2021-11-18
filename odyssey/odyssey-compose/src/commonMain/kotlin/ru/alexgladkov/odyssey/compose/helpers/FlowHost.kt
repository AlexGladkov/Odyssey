package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import ru.alexgladkov.odyssey.compose.extensions.launchAsState
import ru.alexgladkov.odyssey.compose.extensions.observeAsState
import ru.alexgladkov.odyssey.core.NavigationEntry
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
    
    val currentStackSize = flowRootController.backStack.size
    val transitionTime = 500

    AnimatedContent(
        targetState = navigation,
        transitionSpec = {
            if (previousBackstack < currentStackSize) {
                (slideInHorizontally(
                    animationSpec = tween(transitionTime),
                    initialOffsetX = { width -> width })
                        + fadeIn(animationSpec = tween(transitionTime)) with
                        slideOutHorizontally(
                            animationSpec = tween(transitionTime),
                            targetOffsetX = { width -> -width })
                        + fadeOut(animationSpec = tween(transitionTime)))
                    .using(
                        SizeTransform(clip = false)
                    )
            } else {
                (slideInHorizontally(animationSpec = tween(transitionTime), initialOffsetX = { width -> -width })
                        + fadeIn(animationSpec = tween(transitionTime)) with
                        slideOutHorizontally(
                            animationSpec = tween(transitionTime), targetOffsetX = { width -> width })
                        + fadeOut(animationSpec = tween(transitionTime))
                        )
                    .using(
                        SizeTransform(clip = false)
                    )
            }
        }
    ) { target ->
        target?.destination?.destinationName()?.let {
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

    val params = (navigation.value?.destination as? DestinationScreen)?.params
    navigation.value?.let { entry ->
        val render = screenBundle.screenMap[entry.destination.destinationName()]
        render?.invoke(
            screenBundle.copy(params = if (flowRootController.backStack.size == 1) screenBundle.params else params)
        )
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
    val navigation = flowRootController.backStackObserver
        .launchAsState(key = navigationEntry, initial = flowRootController.backStack.last())

    val params = (navigation?.destination as? DestinationScreen)?.params
    navigation?.let { entry ->
        val render = screenBundle.screenMap[entry.destination.destinationName()]
        render?.invoke(
            screenBundle.copy(params = if (flowRootController.backStack.size == 1) screenBundle.params else params)
        )
    }
}