package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
actual fun extractWindowHeight(): Dp {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp.dp
}