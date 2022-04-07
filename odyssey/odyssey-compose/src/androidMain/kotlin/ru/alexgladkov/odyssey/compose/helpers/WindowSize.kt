package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
actual fun extractWindowHeight(): Int {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    return with(density) { configuration.screenHeightDp.dp.toPx().roundToInt() }
}