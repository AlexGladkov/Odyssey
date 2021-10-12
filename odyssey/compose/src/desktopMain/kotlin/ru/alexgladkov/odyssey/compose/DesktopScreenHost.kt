package ru.alexgladkov.odyssey.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.awt.ComposePanel
import kotlinx.coroutines.flow.MutableStateFlow
import ru.alexgladkov.odyssey.compose.extensions.observeAsState
import ru.alexgladkov.odyssey.core.ScreenHost
import ru.alexgladkov.odyssey.core.destination.DestinationPoint
import ru.alexgladkov.odyssey.core.extensions.wrap
import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.WindowConstants

/**
 * Class provider to set canvas to draw navigation
 * @param window - root window of main function in Swing Application
 */
abstract class DesktopScreenHost constructor(
    private val window: JFrame,
) : ScreenHost {

    private val destinationObserver: MutableStateFlow<DestinationPoint?> = MutableStateFlow(null)

    override fun prepareFowDrawing() {
        val composePanel = ComposePanel()

        composePanel.setContent {
            val destinationState = destinationObserver.wrap().observeAsState()
            destinationState.value?.let {
                launchScreen(it)
            }
        }

        window.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        window.contentPane.add(composePanel, BorderLayout.CENTER)
        window.setLocationRelativeTo(null)
        window.isVisible = true
    }

    override fun draw(destinationPoint: DestinationPoint) {
        destinationObserver.tryEmit(destinationPoint)
    }

    @Composable
    protected abstract fun launchScreen(destinationPoint: DestinationPoint)
}