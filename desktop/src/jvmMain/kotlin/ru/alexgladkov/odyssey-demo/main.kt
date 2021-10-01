package ru.alexgladkov.`odyssey-demo`

import androidx.compose.desktop.Window
import androidx.compose.ui.unit.IntSize
import ru.alexgladkov.common.compose.RootContainer
import ru.alexgladkov.odyssey.core.RootController

fun main() {
    val rootController = RootController()

    Window(
        title = "Odyssey Demo",
        size = IntSize(
            800,
            600
        )
    ) {
        RootContainer(
            rootController = rootController,
        )
    }
}