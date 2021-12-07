package ru.alexgladkov.odyssey.core.destination

import ru.alexgladkov.odyssey.core.NavigationEntry
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.animations.AnimationType

/**
 * One of the types of Destination class.
 * Use it for navigation graph generation.
 * This class describes multi stack flow (bottom navigation for example) with child screens
 * @param name - screen key to find in backstack
 * @param flows - list of Destination Flows to switch
 */
class DestinationMultiFlow(
    val name: String,
    val flows: List<DestinationFlow>
) : Destination {

    override fun destinationName(): String = name
    override fun toString(): String = name
}

fun DestinationMultiFlow.mapToNavigationEntry(rootController: RootController, animationType: AnimationType): NavigationEntry =
    NavigationEntry(destination = this, rootController = rootController, animationType = animationType)