package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlin.math.roundToInt

@OptIn(ExperimentalForeignApi::class)
@Composable
private fun windowSize(): IntSize {
    val viewControllerView = LocalUIViewController.current.view
    val density = LocalDensity.current
    val scale = density.density
    val size = viewControllerView.frame.useContents {
        IntSize(
            width = (size.width * scale).roundToInt(),
            height = (size.height * scale).roundToInt()
        )
    }

    return size
}

@Composable
actual fun extractWindowHeight(): Int = windowSize().height