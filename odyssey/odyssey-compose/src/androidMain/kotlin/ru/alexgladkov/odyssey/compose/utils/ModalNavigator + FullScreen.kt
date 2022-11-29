package ru.alexgladkov.odyssey.compose.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
actual fun ModalSheetView(
    backgroundColor: Color,
    scrimAlpha: Float,
    modal: @Composable BoxScope.() -> Unit,
    content: @Composable () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize()
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .captionBarPadding()
                .imePadding()
                .statusBarsPadding()
        ) {
            content.invoke()
        }

        modal.invoke(this)
    }
}


