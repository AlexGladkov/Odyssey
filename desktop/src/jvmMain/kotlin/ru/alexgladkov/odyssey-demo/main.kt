package ru.alexgladkov.`odyssey-demo`

import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.common.compose.navigation.buildComposeNavigationGraph
import ru.alexgladkov.odyssey.compose.DesktopScreenHost
import ru.alexgladkov.odyssey.compose.extensions.setupWithRootController
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() = SwingUtilities.invokeLater {
    val window = JFrame()
    window.title = "Odyssey Demo"
    window.setSize(800, 600)

    DesktopScreenHost(window)
        .setupWithRootController(
            startScreen = NavigationTree.Root.Splash.toString(),
            block = buildComposeNavigationGraph()
        )
}