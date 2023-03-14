package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import platform.UIKit.UIScreen

@Composable
actual fun extractWindowHeight(): Int {
    val density = LocalDensity.current
    return with(density) { UIScreen.mainScreen.bounds.size }
}