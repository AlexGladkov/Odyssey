package ru.alexgladkov.odyssey.compose.utils

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.core.configuration.DisplayType

@Composable
expect fun ModalSheetView(
    backgroundColor: Color,
    scrimAlpha: Float,
    displayType: DisplayType,
    modal: @Composable BoxScope.() -> Unit,
    content: @Composable () -> Unit
)