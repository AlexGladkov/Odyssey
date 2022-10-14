package ru.alexgladkov.common.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import ru.alexgladkov.common.compose.theme.colors.LocalColors
import ru.alexgladkov.common.compose.theme.colors.OdysseyColors
import ru.alexgladkov.common.compose.theme.colors.darkPalette

@Composable
fun OdysseyTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides darkPalette,
        content = content
    )
}

object Odyssey {
    val color: OdysseyColors
        @Composable
        get() = LocalColors.current
}