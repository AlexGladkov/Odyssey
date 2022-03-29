package ru.alexgladkov.`odyssey-demo`

import ru.alexgladkov.`odyssey-demo`.extensions.setupThemedNavigation
import ru.alexgladkov.common.compose.navigation.customNavScreen
import ru.alexgladkov.common.compose.navigation.mainScreen
import ru.alexgladkov.common.compose.navigation.topNavScreen
import ru.alexgladkov.common.compose.screens.ActionsScreen
import ru.alexgladkov.common.compose.screens.PresentedActionsScreen
import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.screen
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() = SwingUtilities.invokeLater {
    val window = JFrame()
    window.title = "Odyssey Demo"
    window.setSize(800, 600)

    window.setupThemedNavigation("actions") {
        screen("actions") {
            ActionsScreen(count = 0)
        }

        screen("push") {
            ActionsScreen(count = it as? Int)
        }

        flow("present") {
            screen("present_screen") {
                PresentedActionsScreen(count = (it as? Int) ?: 0)
            }
        }

        mainScreen()
        topNavScreen()
        customNavScreen()
    }
}