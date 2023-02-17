package ru.alexgladkov.odyssey.compose.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.configuration.ModalNavigatorConfiguration
import ru.alexgladkov.odyssey.core.configuration.DisplayType

@Composable
actual fun ModalSheetView(
    backgroundColor: Color,
    scrimAlpha: Float,
    displayType: DisplayType,
    modal: @Composable BoxScope.() -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize()
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            content.invoke()
        }

        modal.invoke(this)
    }
}