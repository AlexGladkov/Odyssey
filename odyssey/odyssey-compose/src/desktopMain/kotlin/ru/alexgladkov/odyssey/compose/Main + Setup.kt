package ru.alexgladkov.odyssey.compose

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.awt.ComposePanel
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator
import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.WindowConstants

fun JFrame.setupNavigation(
    startScreen: String,
    vararg providers: ProvidedValue<*>,
    navigationGraph: RootComposeBuilder.() -> Unit
) {
    val rootController = RootComposeBuilder().apply(navigationGraph).build()
    val composePanel = ComposePanel()

    // Below function setup drawing, you can extend it
    // by adding CompositionLocalProviders or something else
    composePanel.setContent {
        CompositionLocalProvider(
            LocalRootController provides rootController
        ) {
            ModalNavigator {
                Navigator(startScreen)
            }
        }
    }

    defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    contentPane.add(composePanel, BorderLayout.CENTER)
    setLocationRelativeTo(null)
    isVisible = true
}