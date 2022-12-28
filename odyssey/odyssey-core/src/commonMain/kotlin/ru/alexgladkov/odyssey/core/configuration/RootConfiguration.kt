package ru.alexgladkov.odyssey.core.configuration

data class RootConfiguration(
    val displayType: DisplayType,
    val rootControllerType: RootControllerType = RootControllerType.Root
)