package ru.alexgladkov.`odyssey-demo`

import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.common.compose.navigation.generateNavigationGraph
import ru.alexgladkov.odyssey.core.RootController
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() = SwingUtilities.invokeLater {
    val window = JFrame()
    window.title = "Odyssey Demo"
    window.setSize(800, 600)

    val screenHost = AppScreenHost(window)
    val rootController = RootController(screenHost)
    rootController.generateNavigationGraph()
    screenHost.prepareFowDrawing()
    rootController.launch(NavigationTree.Root.Splash.toString())
}