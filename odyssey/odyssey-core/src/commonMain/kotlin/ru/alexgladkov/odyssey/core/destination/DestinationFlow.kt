package ru.alexgladkov.odyssey.core.destination

import ru.alexgladkov.odyssey.core.NavigationEntry
import ru.alexgladkov.odyssey.core.RootController

/**
 * One of the types of Destination class.
 * Use it for navigation graph generation.
 * This class describes navigation flow with child screens
 * @param name - screen key to find in backstack
 * @param params - bunch of params needed for this screen
 * @param screens - list of screens for sub navigation
 */
data class DestinationFlow(
    val name: String,
    val params: Any? = null,
    val screens: List<DestinationScreen>
) : Destination {
    override fun destinationName(): String = name
}

fun DestinationFlow.mapToNavigationEntry(rootController: RootController): NavigationEntry =
    NavigationEntry(destination = this, rootController = rootController)