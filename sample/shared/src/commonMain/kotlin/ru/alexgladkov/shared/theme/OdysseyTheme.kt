package ru.alexgladkov.shared.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import ru.alexgladkov.shared.theme.colors.LocalColors
import ru.alexgladkov.shared.theme.colors.OdysseyColors
import ru.alexgladkov.shared.theme.colors.darkPalette

@Composable
internal fun OdysseyTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides darkPalette,
        content = content
    )
}

object Odyssey {
    internal val color: OdysseyColors
        @Composable
        get() = LocalColors.current
}