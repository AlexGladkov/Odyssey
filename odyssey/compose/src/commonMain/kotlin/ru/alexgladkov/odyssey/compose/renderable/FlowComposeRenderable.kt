package ru.alexgladkov.common.compose.`compose-extension`

import androidx.compose.runtime.Composable
import ru.alexgladkov.odyssey.core.Renderable
import ru.alexgladkov.odyssey.core.RootController

data class FlowComposeRenderable(
    val render: @Composable RootController.() -> Unit
) : Renderable
