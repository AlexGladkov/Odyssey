package ru.alexgladkov.`odyssey-demo`

import ru.alexgladkov.`odyssey-demo`.extensions.setupThemedNavigation
import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.common.compose.navigation.navigationGraph
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() = SwingUtilities.invokeLater {
    val window = JFrame()
    window.title = "Odyssey Demo"
    window.setSize(800, 600)

    window.setupThemedNavigation(NavigationTree.Actions.name) { navigationGraph() }
}