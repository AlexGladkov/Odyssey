package ru.alexgladkov.odyssey.compose.utils

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
expect fun ModalSheetView(
    backgroundColor: Color,
    scrimAlpha: Float,
    modal: @Composable BoxScope.() -> Unit,
    content: @Composable () -> Unit
)