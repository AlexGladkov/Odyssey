package ru.alexgladkov.odyssey.compose.navigation.modal_navigation

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.compose.controllers.*
import ru.alexgladkov.odyssey.compose.helpers.noRippleClickable
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.views.AlertDialog
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.views.BottomModalSheet
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

internal fun <T> Int.asTween(): TweenSpec<T> = tween(durationMillis = this, easing = FastOutSlowInEasing)

@Composable
internal fun Screamer(alpha: Float, onCloseClick: () -> Unit) {
    Box(modifier = Modifier
        .noRippleClickable { onCloseClick.invoke() }
        .fillMaxSize().background(Color.Black.copy(alpha = alpha)))
}