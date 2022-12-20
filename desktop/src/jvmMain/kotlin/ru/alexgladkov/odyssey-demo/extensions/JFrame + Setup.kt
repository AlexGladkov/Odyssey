package ru.alexgladkov.`odyssey-demo`.extensions

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.awt.ComposePanel
import ru.alexgladkov.common.compose.theme.Odyssey
import ru.alexgladkov.common.compose.theme.OdysseyTheme
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.configuration.DefaultModalConfiguration
import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.WindowConstants

fun JFrame.setupThemedNavigation(
    startScreen: String,
    vararg providers: ProvidedValue<*>,
    navigationGraph: RootComposeBuilder.() -> Unit
) {

    val composePanel = ComposePanel()

    // Below function setup drawing, you can extend it
    // by adding CompositionLocalProviders or something else
    composePanel.setContent {
        OdysseyTheme {
            val rootController = RootComposeBuilder().apply(navigationGraph).build()
            rootController.backgroundColor = Odyssey.color.primaryBackground

            CompositionLocalProvider(
                *providers,
                LocalRootController provides rootController
            ) {
                ModalNavigator(configuration = DefaultModalConfiguration().copy(displayType = rootController.configuration.displayType)) {
                    Navigator(startScreen = startScreen)
                }
            }
        }
    }

    defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    contentPane.add(composePanel, BorderLayout.CENTER)
    setLocationRelativeTo(null)
    isVisible = true
}