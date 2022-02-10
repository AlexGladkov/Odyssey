package ru.alexgladkov.odyssey.compose

import androidx.compose.ui.awt.ComposePanel
import ru.alexgladkov.odyssey.compose.extensions.observeAsState
import ru.alexgladkov.odyssey.core.extensions.wrap
import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.WindowConstants

/**
 * Class provider to set canvas to draw navigation
 * @param window - root window of main function in Swing Application
 */
//class DesktopScreenHost constructor(
//    private val window: JFrame,
//) : ComposableScreenHost() {
//
//    override fun prepareFowDrawing() {
//        val composePanel = ComposePanel()
//
//        // Below function setup drawing, you can extend it
//        // by adding CompositionLocalProviders or something else
//        composePanel.setContent {
//            val destinationState = destinationObserver.wrap().observeAsState()
//            destinationState.value?.let {
//                launchScreen(it)
//            }
//        }
//
//        window.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
//        window.contentPane.add(composePanel, BorderLayout.CENTER)
//        window.setLocationRelativeTo(null)
//        window.isVisible = true
//    }
//}