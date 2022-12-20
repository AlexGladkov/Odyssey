package ru.alexgladkov.odyssey.compose.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.core.configuration.DisplayType

@Composable
actual fun ModalSheetView(
    backgroundColor: Color,
    scrimAlpha: Float,
    displayType: DisplayType,
    modal: @Composable BoxScope.() -> Unit,
    content: @Composable () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize()
    ) {
        val modifier = when (displayType) {
            DisplayType.EdgeToEdge -> Modifier.fillMaxSize()
            DisplayType.FullScreen -> Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .captionBarPadding()
                .imePadding()
                .statusBarsPadding()
        }

        BoxWithConstraints(
            modifier = modifier
        ) {
            content.invoke()
        }

        modal.invoke(this)
    }
}


