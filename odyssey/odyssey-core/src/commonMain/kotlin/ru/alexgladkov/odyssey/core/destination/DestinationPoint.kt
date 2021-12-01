package ru.alexgladkov.odyssey.core.destination

import ru.alexgladkov.odyssey.core.NavigationEntry
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.animations.AnimationType

/**
 * Inner class for navigation and handle controller between stack transitions
 * @param destinationScreen - point of destination
 * @param rootController - support root controller
 */
data class DestinationPoint(
    val destinationScreen: DestinationScreen,
    val rootController: RootController,
    val animationType: AnimationType
)

internal fun DestinationPoint.mapToNavigationEntry(): NavigationEntry =
    NavigationEntry(destinationScreen, animationType, rootController)