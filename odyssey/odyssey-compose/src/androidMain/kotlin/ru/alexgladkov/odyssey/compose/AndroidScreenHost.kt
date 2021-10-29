package ru.alexgladkov.odyssey.compose

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.alexgladkov.odyssey.compose.extensions.observeAsState
import ru.alexgladkov.odyssey.core.extensions.wrap

/**
 * Class provider to set canvas to draw navigation
 * @param composeActivity - root composable activity
 */
class AndroidScreenHost constructor(
    val composeActivity: ComponentActivity
) : ComposableScreenHost() {

    override fun prepareFowDrawing() {
        composeActivity.setContent {
            val destinationState = destinationObserver.wrap().observeAsState()
            destinationState.value?.let {
                launchScreen(it)
            }
        }
    }
}