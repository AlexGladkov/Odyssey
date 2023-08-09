package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AppKit.NSScreen

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun extractWindowHeight(): Int {
    val density = LocalDensity.current
    return with(density) { NSScreen.mainScreen()?.frame?.size ?: 0 }
}