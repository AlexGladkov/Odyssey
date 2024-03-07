package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun extractWindowHeight(): Int {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current.containerSize
    return with(density) { windowInfo.height }
}