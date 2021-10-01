package ru.alexgladkov.odyssey.core

data class NavigationFlow(val startScreen: NavigationScreen, val destinations: List<NavigationDestination>)

class NavigationFlowBuilder(
    private val startScreen: NavigationScreen,
    val rootController: RootController
) {
    private var destinations: MutableList<NavigationDestination> = mutableListOf()

    fun destination(
        screen: NavigationScreen,
        content: Renderable
    ) {
        destinations.add(NavigationDestination(NavigationStackEntry(screen, null), content))
    }

    fun flow(
        screen: NavigationScreen,
        content: RootController.() -> Renderable
    ) {
        val controller = rootController.newChildRootInstance(screen)
        destinations.add(NavigationDestination(NavigationStackEntry(screen, null), content.invoke(controller)))
    }

    fun build(): NavigationFlow = NavigationFlow(startScreen = startScreen, destinations = destinations)
}
