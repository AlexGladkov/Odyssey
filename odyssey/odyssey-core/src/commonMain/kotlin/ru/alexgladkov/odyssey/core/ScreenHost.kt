package ru.alexgladkov.odyssey.core

import ru.alexgladkov.odyssey.core.destination.DestinationPoint

/**
 * One of the main entity in project
 * Describes entry point to draw with.
 * RootController use it to draw on it like canvas
 */
interface ScreenHost {
    fun prepareFowDrawing()
    fun draw(destinationPoint: DestinationPoint)
}