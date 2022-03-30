package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.rememberWindowState

@Composable
actual fun extractWindowHeight(): Dp {
    val state = rememberWindowState()
    return state.size.height
}