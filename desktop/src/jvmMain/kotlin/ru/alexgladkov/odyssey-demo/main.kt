package ru.alexgladkov.`odyssey-demo`

import ru.alexgladkov.common.compose.navigation.generateGraph
import ru.alexgladkov.odyssey.compose.setupNavigation
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() = SwingUtilities.invokeLater {
    val window = JFrame()
    window.title = "Odyssey Demo"
    window.setSize(800, 600)

    window.setupNavigation("start") {
        generateGraph()
    }
}