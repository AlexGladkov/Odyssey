package ru.alexgladkov.odyssey.compose.utils

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.configuration.ModalNavigatorConfiguration

@Composable
actual fun ModalSheetView(
    backgroundColor: Color,
    scrimAlpha: Float,
    modal: @Composable BoxScope.() -> Unit,
    content: @Composable () -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        content.invoke()
    }
}