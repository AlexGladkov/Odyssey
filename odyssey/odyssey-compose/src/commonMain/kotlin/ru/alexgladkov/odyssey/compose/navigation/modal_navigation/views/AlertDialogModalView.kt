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
import ru.alexgladkov.odyssey.core.animations.AnimationType

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

    val heightInitial = when (bundle.animationType) {
        is AnimationType.Present -> height
        else -> 0
    }

    val dialogAlphaInitial = when (bundle.animationType) {
        is AnimationType.Present -> 1f
        else -> 0f
    }

    var backdropAlphaValue by remember { mutableStateOf(0f) }
    var dialogAlphaValue by remember { mutableStateOf(dialogAlphaInitial) }
    var offsetValue by remember { mutableStateOf(heightInitial) }

    val backdropAlpha by animateFloatAsState(
        targetValue = backdropAlphaValue,
        animationSpec = bundle.animationTime.asTween(),
    )

    val dialogAlpha by animateFloatAsState(
        targetValue = dialogAlphaValue,
        animationSpec = bundle.animationTime.asTween(),
        finishedListener = {
            if (bundle.dialogState == ModalDialogState.Idle) {
                modalController.setTopDialogState(ModalDialogState.Open, bundle.key)
            }
        }
    )

    val offset by animateIntAsState(
        targetValue = offsetValue,
        animationSpec = bundle.animationTime.asTween(),
        finishedListener = {
            if (bundle.dialogState is ModalDialogState.Close) {
                modalController.finishCloseAction(bundle.key)
            }
        }
    )

    Screamer(backdropAlpha) {
        if (bundle.closeOnBackdropClick && bundle.dialogState !is ModalDialogState.Close) {
            modalController.popBackStack(key = bundle.key)
        }
    }

    var cardModifier = modifier.alpha(dialogAlpha)
    when (bundle.animationType) {
        is AnimationType.Present -> cardModifier = cardModifier.offset { IntOffset(x = 0, y = offset) }
        else -> { }
    }

    Card(
        modifier = cardModifier,
        shape = RoundedCornerShape(corner = CornerSize(bundle.cornerRadius))
    ) {
        bundle.content.invoke(bundle.key)
    }

    LaunchedEffect(bundle.dialogState) {
        when (bundle.dialogState) {
            is ModalDialogState.Close -> {
                when (bundle.animationType) {
                    is AnimationType.Present -> {
                        backdropAlphaValue = 0f
                        dialogAlphaValue = 1f
                        offsetValue = height
                    }

                    else -> {
                        backdropAlphaValue = 0f
                        dialogAlphaValue = 0f
                        offsetValue = height
                    }
                }
            }

            else -> {
                backdropAlphaValue = bundle.alpha
                dialogAlphaValue = 1f
                offsetValue = 0
            }
        }
    }
}