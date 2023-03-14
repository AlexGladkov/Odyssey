package ru.alexgladkov.`odyssey-demo`

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ru.alexgladkov.shared.MainView

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Odyssey Desktop Example",
        state = rememberWindowState(
            width = 1024.dp,
            height = 800.dp,
            position = WindowPosition.Aligned(Alignment.Center)
        )
    ) {
        MainView {
            exitApplication()
        }
    }
}