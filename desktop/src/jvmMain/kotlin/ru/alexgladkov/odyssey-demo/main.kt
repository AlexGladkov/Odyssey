package ru.alexgladkov.`odyssey-demo`

import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.common.compose.navigation.generateGraph
import ru.alexgladkov.odyssey.core.RootController
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() = SwingUtilities.invokeLater {
    val window = JFrame()
    window.title = "Odyssey Demo"
    window.setSize(800, 600)

    val screenHost = AppScreenHost(window)
    screenHost.prepareFowDrawing()

    val rootController = RootController(screenHost)
    rootController.setNavigationGraph { generateGraph() }
    rootController.launch(NavigationTree.Root.Splash.toString())
}