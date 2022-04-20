package ru.alexgladkov.odyssey.compose.navigation.modal_navigation.views

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.IntOffset
import ru.alexgladkov.odyssey.compose.controllers.AlertBundle
import ru.alexgladkov.odyssey.compose.controllers.ModalController
import ru.alexgladkov.odyssey.compose.controllers.ModalDialogState
import ru.alexgladkov.odyssey.compose.helpers.extractWindowHeight
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.Screamer
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.asTween

@Composable
internal fun BoxScope.AlertDialog(
    bundle: AlertBundle,
    modalController: ModalController
) {
    val height = extractWindowHeight()

    var modifier = Modifier.align(Alignment.Center)
    if (bundle.maxHeight != null)
        modifier = modifier.fillMaxHeight(bundle.maxHeight)
    if (bundle.maxWidth != null)
        modifier = modifier.fillMaxWidth(bundle.maxWidth)

    var backdropAlphaValue by remember { mutableStateOf(0f) }
    var dialogAlphaValue by remember { mutableStateOf(0f) }
    var offsetValue by remember { mutableStateOf(0) }

    val backdropAlpha by animateFloatAsState(
        targetValue = backdropAlphaValue,
        animationSpec = bundle.animationTime.asTween(),
    )

    val dialogAlpha by animateFloatAsState(
        targetValue = dialogAlphaValue,
        animationSpec = bundle.animationTime.asTween(),
        finishedListener = {
            if (bundle.dialogState == ModalDialogState.Idle) {
                modalController.setTopDialogState(ModalDialogState.Open)
            }
        }
    )

    val offset by animateIntAsState(
        targetValue = offsetValue,
        animationSpec = bundle.animationTime.asTween(),
        finishedListener = {
            if (bundle.dialogState is ModalDialogState.Close) {
                modalController.finishCloseAction()
            }
        }
    )

    Screamer(backdropAlpha) { modalController.popBackStack() }

    Card(
        modifier = modifier.alpha(dialogAlpha).offset { IntOffset(x = 0, y = offset) },
        shape = RoundedCornerShape(corner = CornerSize(bundle.cornerRadius))
    ) {
        bundle.content.invoke()
    }

    LaunchedEffect(bundle.dialogState) {
        when (bundle.dialogState) {
            is ModalDialogState.Close -> {
                backdropAlphaValue = 0f
                dialogAlphaValue = 1f
                offsetValue = height
            }

            else -> {
                backdropAlphaValue = bundle.alpha
                dialogAlphaValue = 1f
                offsetValue = 0
            }
        }
    }
}