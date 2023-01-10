package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import platform.AppKit.NSScreen

@Composable
actual fun extractWindowHeight(): Int {
    val density = LocalDensity.current
    return with(density) { NSScreen.mainScreen()?.frame?.size ?: 0 }
}