package ru.alexgladkov.odyssey.core.extensions

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.awt.ComposePanel
import ru.alexgladkov.odyssey.core.base.Navigator
import ru.alexgladkov.odyssey.core.local.LocalRootController
import ru.alexgladkov.odyssey.core.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.core.navigation.bottom_sheet_navigation.ModalSheetNavigator
import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.WindowConstants

fun JFrame.setupNavigation(
    startScreen: String,
    vararg providers: ProvidedValue<*>,
    navigationGraph: RootComposeBuilder.() -> Unit
) {
    val rootController = RootComposeBuilder().apply(navigationGraph).build()

    defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    title = "OdysseyDemo"

    val composePanel = ComposePanel()
    composePanel.setContent {
        CompositionLocalProvider(
            *providers,
            LocalRootController provides rootController
        ) {
            ModalSheetNavigator {
                Navigator(startScreen)
            }
        }
    }

    contentPane.add(composePanel, BorderLayout.CENTER)
    setSize(800, 600)
    setLocationRelativeTo(null)
    isVisible = true
}