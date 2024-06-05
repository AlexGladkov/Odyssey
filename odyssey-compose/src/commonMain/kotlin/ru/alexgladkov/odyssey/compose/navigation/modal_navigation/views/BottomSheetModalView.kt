package ru.alexgladkov.odyssey.compose.navigation.modal_navigation.views

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ru.alexgladkov.odyssey.compose.controllers.ModalController
import ru.alexgladkov.odyssey.compose.controllers.ModalDialogState
import ru.alexgladkov.odyssey.compose.controllers.ModalSheetBundle
import ru.alexgladkov.odyssey.compose.helpers.extractWindowHeight
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.Screamer
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.asTween

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun BoxScope.BottomModalSheet(
    backgroundColor: Color,
    bundle: ModalSheetBundle,
    modalController: ModalController,
) {
    val height = extractWindowHeight()
    val viewHeight by derivedStateOf { (height * (bundle.maxHeight ?: 1f)) }
    val swipeableState = rememberSwipeableState(0)

    var modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth()

    val density = LocalDensity.current

    if (bundle.maxHeight != null)
        modifier = modifier
            .padding(top = with(density) { (height - viewHeight).toDp() })
            .fillMaxHeight()

    var offsetValue by remember { mutableStateOf(height) }
    var backdropAlphaValue by remember { mutableStateOf(0f) }
    val anchors = mapOf(0f to 0, viewHeight to 1)

    val backdropAlpha by animateFloatAsState(
        targetValue = backdropAlphaValue,
        animationSpec = bundle.animationTime.asTween(),
    )

    val offset by animateIntAsState(
        targetValue = offsetValue,
        animationSpec = bundle.animationTime.asTween(),
        finishedListener = {
            when (bundle.dialogState) {
                ModalDialogState.Idle -> modalController.setTopDialogState(ModalDialogState.Open, bundle.key)
                is ModalDialogState.Close -> modalController.finishCloseAction(bundle.key)
                ModalDialogState.Open -> {}
            }
        }
    )

    if (bundle.backContent != null) {
        bundle.backContent.invoke(bundle.key)
    } else {
        Screamer(backdropAlpha) {
            if (bundle.closeOnBackdropClick && bundle.dialogState !is ModalDialogState.Close) {
                modalController.popBackStack(key = bundle.key)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (bundle.closeOnSwipe) {
            modifier = modifier.swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(bundle.threshold) },
                orientation = Orientation.Vertical
            ).offset {
                val swipeOffset = swipeableState.offset.value
                val positiveOffset = if (swipeOffset < 0) 0 else swipeOffset

                IntOffset(x = 0, y = offset + positiveOffset.toInt())
            }
        }

        Box(
            modifier = modifier
                .clip(shape = RoundedCornerShape(
                    topStart = bundle.cornerRadius.dp,
                    topEnd = bundle.cornerRadius.dp
                ))
                .background(backgroundColor)
            ,
        ) {
            bundle.content.invoke(bundle.key)
        }
    }

    LaunchedEffect(bundle.dialogState, swipeableState.offset.value) {
        if (swipeableState.offset.value == viewHeight && bundle.dialogState !is ModalDialogState.Close) {
            modalController.setTopDialogState(ModalDialogState.Close(animate = false), bundle.key)
        }

        when (bundle.dialogState) {
            is ModalDialogState.Close -> {
                offsetValue = height
                backdropAlphaValue = 0f
            }

            else -> {
                offsetValue = 0
                backdropAlphaValue = bundle.alpha
            }
        }
    }
}