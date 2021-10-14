package ru.alexgladkov.odyssey.core.destination

import ru.alexgladkov.odyssey.core.NavigationEntry
import ru.alexgladkov.odyssey.core.RootController

/**
 * Label interface for graph generation and navigation
 * @see DestinationScreen for simple screen
 * @see DestinationFlow for screen flow
 * @see DestinationMultiFlow for multistack flow
 */
interface Destination {
    fun destinationName(): String
}

fun Destination.mapToNavigationEntry(rootController: RootController): NavigationEntry =
    NavigationEntry(this, rootController)