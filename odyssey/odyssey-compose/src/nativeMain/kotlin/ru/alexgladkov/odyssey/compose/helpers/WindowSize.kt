package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity

@Composable
actual fun extractWindowHeight(): Int {
    val density = LocalDensity.current
    return with(density) { throw NotImplementedError() }
}