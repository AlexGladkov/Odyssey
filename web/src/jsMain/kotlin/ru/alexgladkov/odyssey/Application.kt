package ru.alexgladkov.odyssey

import kotlinx.browser.document
import org.jetbrains.compose.common.material.Text
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLElement
import ru.alexgladkov.odyssey.core.RootController

fun main() {
    val rootElement = document.getElementById("root") as HTMLElement

    val rootController = RootController()

    renderComposable(root = rootElement) {
        Text("Hello JS")
    }
}