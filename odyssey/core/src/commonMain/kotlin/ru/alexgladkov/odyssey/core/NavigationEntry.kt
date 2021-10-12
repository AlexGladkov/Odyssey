package ru.alexgladkov.odyssey.core

import ru.alexgladkov.odyssey.core.destination.Destination

data class NavigationEntry(
    val destination: Destination,
    val rootController: RootController
)