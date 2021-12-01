package ru.alexgladkov.odyssey.compose

import androidx.compose.runtime.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.alexgladkov.odyssey.compose.animations.AnimatedTransition
import ru.alexgladkov.odyssey.compose.extensions.observeAsState
import ru.alexgladkov.odyssey.compose.helpers.FlowHost
import ru.alexgladkov.odyssey.compose.helpers.MutableScreenMap
import ru.alexgladkov.odyssey.compose.helpers.ScreenBundle
import ru.alexgladkov.odyssey.compose.helpers.ScreenMap
import ru.alexgladkov.odyssey.core.ScreenHost
import ru.alexgladkov.odyssey.core.destination.DestinationFlow
import ru.alexgladkov.odyssey.core.destination.DestinationMultiFlow
import ru.alexgladkov.odyssey.core.destination.DestinationPoint
import ru.alexgladkov.odyssey.core.destination.DestinationScreen


/**
 * Base class for connecting RootController and Composable canvas
 */
abstract class ComposableScreenHost : ScreenHost {

    private val _destinationMap: MutableScreenMap = hashMapOf()
    private val _destinationObserver: MutableStateFlow<DestinationPoint?> = MutableStateFlow(null)

    val destinationObserver: StateFlow<DestinationPoint?> = _destinationObserver

    fun setScreenMap(map: ScreenMap) {
        _destinationMap.clear()
        _destinationMap.putAll(map)
    }

    override fun draw(destinationPoint: DestinationPoint) {
        _destinationObserver.tryEmit(destinationPoint)
    }

    @Composable
    protected fun launchScreen(destinationPoint: DestinationPoint) {
        val state = destinationPoint.rootController.backStackObserver
            .observeAsState(destinationPoint.rootController.backStack.last())

        val destinationStackCount = destinationPoint.rootController.backStack.size
        var currentStackCount by remember { mutableStateOf(destinationPoint.rootController.backStack.size) }

        state.value?.let { navigationEntry ->
            AnimatedTransition(
                targetState = navigationEntry,
                isForwardDirection = destinationStackCount > currentStackCount,
                animation = navigationEntry.animationType,
                onAnimationEnd = {
                    currentStackCount = destinationStackCount
                }
            ) {
                val entry = this
                when (val destination = entry.destination) {
                    is DestinationScreen -> {
                        val render = _destinationMap[entry.destination.destinationName()]
                        render?.invoke(
                            ScreenBundle(
                                rootController = destinationPoint.rootController,
                                params = destination.params,
                                screenMap = _destinationMap
                            )
                        )
                    }

                    is DestinationFlow -> {
                        FlowHost(
                            ScreenBundle(
                                rootController = entry.rootController,
                                params = destination.params,
                                screenMap = _destinationMap
                            )
                        )
                    }

                    is DestinationMultiFlow -> {
                        _destinationMap[destination.name]
                            ?.invoke(
                                ScreenBundle(
                                    rootController = entry.rootController,
                                    screenMap = _destinationMap
                                )
                            )
                    }

                    else -> throw IllegalStateException("This destination type isn't implemented")
                }
            }
        }
    }

    abstract override fun prepareFowDrawing()
}