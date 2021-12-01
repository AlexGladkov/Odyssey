package ru.alexgladkov.odyssey.core.destination

import ru.alexgladkov.odyssey.core.NavigationEntry
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.animations.AnimationType

/**
 * Label interface for graph generation and navigation
 * @see DestinationScreen for simple screen
 * @see DestinationFlow for screen flow
 * @see DestinationMultiFlow for multistack flow
 *
 * @param destinationName - Represent screen ID
 */
interface Destination {
    fun destinationName(): String
}

fun Destination.mapToNavigationEntry(rootController: RootController, animationType: AnimationType): NavigationEntry =
    NavigationEntry(this, animationType, rootController)