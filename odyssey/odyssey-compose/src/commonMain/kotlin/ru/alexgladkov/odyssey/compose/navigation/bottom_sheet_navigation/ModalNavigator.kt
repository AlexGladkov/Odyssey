package ru.alexgladkov.odyssey.compose.navigation.bottom_sheet_navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import ru.alexgladkov.odyssey.compose.animations.AnimatedTransition
import ru.alexgladkov.odyssey.compose.controllers.*
import ru.alexgladkov.odyssey.compose.helpers.noRippleClickable
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.animations.defaultPresentationAnimation
import ru.alexgladkov.odyssey.core.extensions.Closeable

@Deprecated("@see ModalNavigator", ReplaceWith("ModalNavigator(content)"))
@Composable
fun ModalSheetNavigator(content: @Composable () -> Unit) = ModalNavigator(content)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModalNavigator(
    content: @Composable () -> Unit
) {
    val modalController = remember { ModalController() }
    val rootController = LocalRootController.current
    var modalStack: List<ModalBundle> by remember { mutableStateOf(emptyList()) }
    var closeable: Closeable? = null

    rootController.attachModalController(modalController)
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        content.invoke()

        modalStack.forEach { bundle ->
            when (bundle) {
                is ModalSheetBundle -> {
                    BottomModalSheet(bundle, modalController)
                }
                is AlertBundle -> {
                    AlertDialog(bundle, modalController)
                }
                is CustomModalBundle -> {
                    bundle.content.invoke()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        closeable = modalController.currentStack.watch {
            modalStack = it
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            closeable?.close()
        }
    }
}

@Composable
private fun BoxScope.BottomModalSheet(
    bundle: ModalSheetBundle,
    modalController: ModalController
) {
    var modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth()
    if (bundle.maxHeight != null)
        modifier = modifier.fillMaxHeight(bundle.maxHeight)
    if (bundle.backContent != null) {
        bundle.backContent.invoke()
    } else {
        Screamer(bundle.alpha) { modalController.removeTopScreen() }
    }

//    var offset by remember { mutableStateOf(320.dp) }
    var offsetValue by remember { mutableStateOf(350.dp) }
    val offset by animateDpAsState(
        targetValue = offsetValue,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = modifier.offset(y = offset),
            shape = RoundedCornerShape(
                topStart = bundle.cornerRadius.dp,
                topEnd = bundle.cornerRadius.dp
            )
        ) {
            bundle.content.invoke()
        }
    }

    LaunchedEffect(Unit) {
        offsetValue = 0.dp
//        val animationTime = 0.5
//        val tick = 10L
//
//        val step = (tick / (animationTime * 1000L).toFloat()) * 100
//
//        launch {
//            while (offset > (-16).dp) {
//                delay(tick)
//                println("Debug $offset")
//                offset -= step.dp
//            }
//        }
    }
}

@Composable
private fun BoxScope.AlertDialog(
    bundle: AlertBundle,
    modalController: ModalController
) {
    var modifier = Modifier.align(Alignment.Center)
    if (bundle.maxHeight != null)
        modifier = modifier.fillMaxHeight(bundle.maxHeight)
    if (bundle.maxWidth != null)
        modifier = modifier.fillMaxWidth(bundle.maxWidth)
    Screamer(bundle.alpha) { modalController.removeTopScreen() }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(corner = CornerSize(bundle.cornerRadius))
    ) {
        bundle.content.invoke()
    }
}

@Composable
private fun Screamer(alpha: Float, onCloseClick: () -> Unit) {
    Box(modifier = Modifier
        .noRippleClickable { onCloseClick.invoke() }
        .fillMaxSize().background(Color.Black.copy(alpha = alpha)))
}