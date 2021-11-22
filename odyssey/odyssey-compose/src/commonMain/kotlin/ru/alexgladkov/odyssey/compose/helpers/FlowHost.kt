package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.alexgladkov.odyssey.compose.extensions.observeAsState
import ru.alexgladkov.odyssey.core.controllers.FlowRootController
import ru.alexgladkov.odyssey.core.destination.DestinationScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FlowHost(screenBundle: ScreenBundle) {
    val flowRootController = screenBundle.rootController as FlowRootController
    val navigation by flowRootController.backStackObserver.observeAsState()
    var currentStackCount by remember { mutableStateOf(flowRootController.backStack.size) }

    // This ugly hack needs to prevent double display for previous flow state.
    // Fixme: need to fix it for navigation optimization
    if (flowRootController.allowedDestinations.map { it.destinationName() }
            .contains(navigation?.destination?.destinationName().orEmpty())) {
        val params = (navigation?.destination as? DestinationScreen)?.params
        val destinationStackCount = flowRootController.backStack.size
        val transitionTime = 500

        AnimatedContentWithCallback(
            targetState = navigation,
            transitionSpec = {
                if (destinationStackCount > currentStackCount) {
                    // Forward animation
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
            },
            onAnimationEnd = {
                println("on animation end $destinationStackCount")
                currentStackCount = destinationStackCount
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
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <S> AnimatedContentWithCallback(
    targetState: S,
    modifier: Modifier = Modifier,
    transitionSpec: AnimatedContentScope<S>.() -> ContentTransform = {
        fadeIn(animationSpec = tween(220, delayMillis = 90)) +
                scaleIn(initialScale = 0.92f, animationSpec = tween(220, delayMillis = 90)) with
                fadeOut(animationSpec = tween(90))
    },
    contentAlignment: Alignment = Alignment.TopStart,
    onAnimationEnd: () -> Unit,
    content: @Composable AnimatedVisibilityScope.(targetState: S) -> Unit
) {
    var previousValue by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = targetState, label = "AnimatedContent")

    if (transition.isRunning != previousValue) {
        previousValue = transition.isRunning

        println("Transition ${transition.isRunning}")
        if (!transition.isRunning) {
            onAnimationEnd()
        }
    }

    transition.AnimatedContent(
        modifier,
        transitionSpec,
        contentAlignment,
        content = content
    )
}