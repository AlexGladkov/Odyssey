package ru.alexgladkov.odyssey.core

import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.destination.Destination

data class NavigationEntry(
    val destination: Destination,
    val animationType: AnimationType,
    val rootController: RootController
)