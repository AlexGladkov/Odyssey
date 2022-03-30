package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.rememberWindowState
import kotlin.math.roundToInt

@Composable
actual fun extractWindowHeight(): Int {
    val density = LocalDensity.current
    val state = rememberWindowState()
    return with(density) { state.size.height.toPx().roundToInt() }
}