package ru.alexgladkov.common.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import ru.alexgladkov.common.compose.theme.colors.LocalColors
import ru.alexgladkov.common.compose.theme.colors.OdysseyColors
import ru.alexgladkov.common.compose.theme.colors.darkPalette
import ru.alexgladkov.common.compose.theme.colors.lightPalette

@Composable
fun OdysseyTheme(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides if (!isDarkTheme) lightPalette else darkPalette,
        content = content
    )
}

object Odyssey {
    val color: OdysseyColors
        @Composable
        get() = LocalColors.current
}