package ru.alexgladkov.`odyssey-demo`

import ru.alexgladkov.common.root.navigation.generateGraph
import ru.alexgladkov.odyssey.core.extensions.setupNavigation
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