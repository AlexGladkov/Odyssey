package ru.alexgladkov.common.compose.`compose-extension`

import androidx.compose.runtime.Composable
import ru.alexgladkov.odyssey.core.NavigationStackEntry
import ru.alexgladkov.odyssey.core.Renderable

data class ComposeRenderable(
    val render: @Composable NavigationStackEntry.() -> Unit
) : Renderable
