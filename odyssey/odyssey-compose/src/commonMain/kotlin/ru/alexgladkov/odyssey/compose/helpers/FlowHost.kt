package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.odyssey.compose.extensions.observeAsState
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.controllers.FlowRootController
import ru.alexgladkov.odyssey.core.destination.DestinationScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FlowHost(screenBundle: ScreenBundle) {
    val flowRootController = screenBundle.rootController as FlowRootController
    val navigation by flowRootController.backStackObserver.observeAsState()
    var previousBackstack = remember { flowRootController.backStack.size }

    // This ugly hack needs to prevent double display for previous flow state.
    // Fixme: need to fix it for navigation optimization
    if (flowRootController.allowedDestinations.map { it.destinationName() }
            .contains(navigation?.destination?.destinationName().orEmpty())) {
        val params = (navigation?.destination as? DestinationScreen)?.params
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
    }
}

@Composable
fun testCount(count: Int, onCountClick: () -> Unit) {
    Column {
        Text("$count")
        Button(modifier = Modifier.padding(top = 16.dp), onClick = onCountClick) {
            Text("Change Visibility")
        }
    }
}

@Composable
fun LoginScreen(rootController: RootController, source: String? = null, onNextClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(24.dp),
            text = "Login Screen from $source", fontWeight = FontWeight.Medium, fontSize = 28.sp,
            color = Color.Black
        )

        Row(modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth().padding(16.dp)) {
            Button(onClick = {
                rootController.parentRootController?.popBackStack()
            }) {
                Text("Go back")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = onNextClick) {
                Text("Go to Confirm Code")
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginCodeScreen(rootController: RootController, type: String?, onBackClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(24.dp),
            text = "Login Code Screen (${type})", fontWeight = FontWeight.Medium, fontSize = 28.sp,
            color = Color.Black
        )

        Row(modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth().padding(16.dp)) {
            Button(onClick = onBackClick) {
                Text("Go back")
            }
        }
    }
}