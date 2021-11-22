package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
    val currentScreen = flowRootController.backStackObserver.observeAsState(flowRootController.backStack.last())
    var previousScreen by remember { mutableStateOf(flowRootController.backStack.last()) }

    var previousRender by remember { mutableStateOf(screenBundle.screenMap[previousScreen.destination.destinationName()]) }
    var currentRender by remember { mutableStateOf(screenBundle.screenMap[currentScreen.value?.destination?.destinationName()]) }

    val params = (currentScreen.value?.destination as? DestinationScreen)?.params

    val currentStackSize = flowRootController.backStack.size
    val transitionTime = 15000

//    navigation.value?.destination?.destinationName()?.let {
//        EnterAnimation(navigation) {
//            val text = remember { mutableStateOf("text value") }
//
//            Column {
//                Text(text.value)
//                Button(onClick = {
//                    text.value = "${Random.nextInt()}"
//                }) {
//                    Text("Next")
//                }
//            }
//        }
//    }

//    var count by remember { mutableStateOf(0) }
//
//    Column {
//        Box(modifier = Modifier.height(300.dp).fillMaxWidth()) {
//            AnimatedContent(
//                targetState = navigation,
//                transitionSpec = {
//                    // Compare the incoming number with the previous number.
//                    if (targetState != initialState) {
//                        // If the target number is larger, it slides up and fades in
//                        // while the initial (smaller) number slides up and fades out.
//                        slideInVertically { height -> height } + fadeIn() with
//                                slideOutVertically { height -> -height } + fadeOut()
//                    } else {
//                        // If the target number is smaller, it slides down and fades in
//                        // while the initial number slides down and fades out.
//                        slideInVertically { height -> -height } + fadeIn() with
//                                slideOutVertically { height -> height } + fadeOut()
//                    }.using(
//                        // Disable clipping since the faded slide-in/out should
//                        // be displayed out of bounds.
//                        SizeTransform(clip = false)
//                    )
//                }
//            ) { targetCount ->
//                navigation.value?.let { entry ->
//                    val render = screenBundle.screenMap[entry.destination.destinationName()]
//                    render?.invoke(
//                        screenBundle.copy(params = if (flowRootController.backStack.size == 1) screenBundle.params else params)
//                    )
//                }
//            }
//        }
//
//        Button(onClick = {
//            count++
//        }) {
//            Text("Next")
//        }
//    }

    var currentPosition by remember { mutableStateOf(1f) }
    var previousPosition by remember { mutableStateOf(1f) }

    LaunchedEffect(currentScreen.value) {
        if (previousScreen.destination.destinationName() == currentScreen.value?.destination?.destinationName()) {
            previousScreen = currentScreen.value!!
            previousRender = currentRender
        } else {
            println("Current -> ${currentScreen.value?.destination?.destinationName()}")
            println("Previous -> ${previousScreen.destination.destinationName()}")
            launch {
                previousPosition = 1001f
                while (true) {
                    currentPosition -= 2f
                    previousPosition -= 2f

                    delay(1L)
                    if (currentPosition == 1001f) {
                        previousScreen = currentScreen.value!!
                        previousRender = currentRender

                        currentPosition = 1f
                        break
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.graphicsLayer {
                translationX = currentPosition
            }
        ) {
            currentRender?.invoke(
                screenBundle.copy(params = if (flowRootController.backStack.size == 1) screenBundle.params else params)
            )
        }

        Box(
            modifier = Modifier.graphicsLayer {
                translationX = previousPosition
            }
        ) {
            previousRender?.invoke(screenBundle)
        }
    }
}

@Composable
fun Test() {
    var scale by remember { mutableStateOf(1f) }
    val coroutine = rememberCoroutineScope()
    var text by remember { mutableStateOf(0) }
    var color by remember { mutableStateOf(androidx.compose.ui.graphics.Color.Green) }

    Row(
        modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 10.dp)
                .graphicsLayer {
                    translationX = scale
                }, backgroundColor = color
        ) {
            Text(
                text = text.toString(),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 30.dp),
                fontSize = 30.sp
            )
        }
    }
    Row(
        modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 10.dp)
                .graphicsLayer { translationX = scale - 1002f },
            backgroundColor = if (color == androidx.compose.ui.graphics.Color.Yellow)
                androidx.compose.ui.graphics.Color.Green else
                androidx.compose.ui.graphics.Color.Yellow
        ) {
            Text(
                text = (text + 1).toString(), textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 30.dp), fontSize = 30.sp
            )
        }
    }
    Column(
        Modifier.fillMaxSize().padding(bottom = 100.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            coroutine.launch {
                while (scale != 1001f) {
                    scale += 2f
                    delay(1L)
                    if (scale == 1001f) {
                        text++
                        scale = 1f
                        color = if (color == androidx.compose.ui.graphics.Color.Yellow)
                            androidx.compose.ui.graphics.Color.Green else
                            androidx.compose.ui.graphics.Color.Yellow
                        break
                    }
                }
            }
        }) { Text(text = "start") }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EnterAnimation(navigation: State<NavigationEntry?>, content: @Composable () -> Unit) {
    val transitionTime = 15000

    AnimatedContent(
        targetState = navigation,
        transitionSpec = {
//            if (previousBackstack < currentStackSize) {
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
//            } else {
//                (slideInHorizontally(animationSpec = tween(transitionTime), initialOffsetX = { width -> -width })
//                        + fadeIn(animationSpec = tween(transitionTime)) with
//                        slideOutHorizontally(
//                            animationSpec = tween(transitionTime), targetOffsetX = { width -> width })
//                        + fadeOut(animationSpec = tween(transitionTime))
//                        )
//                    .using(
//                        SizeTransform(clip = false)
//                    )
//            }
        }
    ) {
        content()
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