package ru.alexgladkov.odyssey.compose

import androidx.compose.runtime.Composable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import kotlinx.coroutines.flow.MutableStateFlow
import ru.alexgladkov.odyssey.compose.extensions.observeAsState
import ru.alexgladkov.odyssey.core.ScreenHost
import ru.alexgladkov.odyssey.core.destination.DestinationPoint
import ru.alexgladkov.odyssey.core.extensions.wrap

/**
 * Class provider to set canvas to draw navigation
 * @param composeActivity - root composable activity
 */
abstract class AndroidScreenHost constructor(
    private val composeActivity: ComponentActivity
) : ScreenHost {

    private val destinationObserver: MutableStateFlow<DestinationPoint?> = MutableStateFlow(null)

    override fun prepareFowDrawing() {
        composeActivity.setContent {
            val destinationState = destinationObserver.wrap().observeAsState()
            destinationState.value?.let {
                launchScreen(it)
            }
        }
    }

    override fun draw(destinationPoint: DestinationPoint) {
        destinationObserver.tryEmit(destinationPoint)
    }

    @Composable
    protected abstract fun launchScreen(destinationPoint: DestinationPoint)
}