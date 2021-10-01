package ru.alexgladkov.odyssey.core

interface NavigationScreen

data class NavigationStackEntry(
    val screen: NavigationScreen,
    val params: Any? = null
)

class NavigationDestination(
    val entry: NavigationStackEntry,
    val content: Renderable
)