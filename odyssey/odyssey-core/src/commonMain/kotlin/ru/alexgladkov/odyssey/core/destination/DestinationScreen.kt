package ru.alexgladkov.odyssey.core.destination

import ru.alexgladkov.odyssey.core.NavigationEntry
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.animations.AnimationType

/**
 * One of the types of Destination class.
 * Use it for navigation graph generation.
 * This class describes simple screen.
 * @param name - screen key to find in backstack
 * @param params - bunch of params needed for this screen
 */
class DestinationScreen(
    val name: String,
    val params: Any? = null
) : Destination {

    override fun destinationName(): String = name

    override fun toString(): String {
        return name
    }
}

fun DestinationScreen.mapToNavigationEntry(rootController: RootController, animationType: AnimationType): NavigationEntry =
    NavigationEntry(destination = this, rootController = rootController, animationType = animationType)