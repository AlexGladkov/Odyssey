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
import ru.alexgladkov.odyssey.compose.helpers.extractWindowHeight
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
    modalController: ModalController,
) {
    val animationTime = 400
    val height = extractWindowHeight()
    var modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth()
    if (bundle.maxHeight != null)
        modifier = modifier.fillMaxHeight(bundle.maxHeight)

    var offsetValue by remember { mutableStateOf(height) }
    var backdropAlphaValue by remember { mutableStateOf(0f) }

    val backdropAlpha by animateFloatAsState(
        targetValue = backdropAlphaValue,
        animationSpec = tween(durationMillis = animationTime, easing = FastOutSlowInEasing),
    )

    val offset by animateDpAsState(
        targetValue = offsetValue,
        animationSpec = tween(durationMillis = animationTime, easing = FastOutSlowInEasing),
        finishedListener = {
            when (bundle.dialogState) {
                ModalDialogState.IDLE -> modalController.setTopDialogState(ModalDialogState.OPEN)
                ModalDialogState.ClOSE -> modalController.finishCloseAction()
            }
        }
    )

    if (bundle.backContent != null) {
        bundle.backContent.invoke()
    } else {
        Screamer(backdropAlpha) { modalController.popBackStack() }
    }

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

    LaunchedEffect(bundle.dialogState) {
        when (bundle.dialogState) {
            ModalDialogState.ClOSE -> {
                offsetValue = height
                backdropAlphaValue = 0f
            }

            else -> {
                offsetValue = 0.dp
                backdropAlphaValue = bundle.alpha
            }
        }
    }
}

@Composable
private fun BoxScope.AlertDialog(
    bundle: AlertBundle,
    modalController: ModalController
) {
    val animationTime = 400
    val height = extractWindowHeight()

    var modifier = Modifier.align(Alignment.Center)
    if (bundle.maxHeight != null)
        modifier = modifier.fillMaxHeight(bundle.maxHeight)
    if (bundle.maxWidth != null)
        modifier = modifier.fillMaxWidth(bundle.maxWidth)

    var backdropAlphaValue by remember { mutableStateOf(0f) }
    var dialogAlphaValue by remember { mutableStateOf(0f) }
    var offsetValue by remember { mutableStateOf(0.dp) }

    val backdropAlpha by animateFloatAsState(
        targetValue = backdropAlphaValue,
        animationSpec = tween(durationMillis = animationTime, easing = FastOutSlowInEasing),
    )

    val dialogAlpha by animateFloatAsState(
        targetValue = dialogAlphaValue,
        animationSpec = tween(durationMillis = animationTime, easing = FastOutSlowInEasing),
        finishedListener = {
            if (bundle.dialogState == ModalDialogState.IDLE) {
                modalController.setTopDialogState(ModalDialogState.OPEN)
            }
        }
    )

    val offset by animateDpAsState(
        targetValue = offsetValue,
        animationSpec = tween(durationMillis = animationTime, easing = FastOutSlowInEasing),
        finishedListener = {
            if (bundle.dialogState == ModalDialogState.ClOSE) {
                modalController.finishCloseAction()
            }
        }
    )

    Screamer(backdropAlpha) { modalController.popBackStack() }

    Card(
        modifier = modifier.alpha(dialogAlpha).offset(y = offset),
        shape = RoundedCornerShape(corner = CornerSize(bundle.cornerRadius))
    ) {
        bundle.content.invoke()
    }

    LaunchedEffect(bundle.dialogState) {
        when (bundle.dialogState) {
            ModalDialogState.ClOSE -> {
                backdropAlphaValue = 0f
                dialogAlphaValue = 1f
                offsetValue = height
            }

            else -> {
                backdropAlphaValue = bundle.alpha
                dialogAlphaValue = 1f
                offsetValue = 0.dp
            }
        }
    }
}

@Composable
private fun Screamer(alpha: Float, onCloseClick: () -> Unit) {
    Box(modifier = Modifier
        .noRippleClickable { onCloseClick.invoke() }
        .fillMaxSize().background(Color.Black.copy(alpha = alpha)))
}