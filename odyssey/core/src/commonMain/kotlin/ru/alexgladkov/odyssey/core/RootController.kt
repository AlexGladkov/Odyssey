package ru.alexgladkov.odyssey.core

import kotlinx.coroutines.flow.MutableStateFlow
import ru.alexgladkov.odyssey.core.backpress.BackPressedCallback
import ru.alexgladkov.odyssey.core.backpress.OnBackPressedDispatcher
import ru.alexgladkov.odyssey.core.extensions.CFlow
import ru.alexgladkov.odyssey.core.extensions.wrap

class RootController {
    var parentRoot: RootController? = null
    var childRoot: RootController? = null
    var debugName: String? = null

    private val _childrenRootControllers: MutableMap<String, RootController?> = mutableMapOf()
    private var _onBackPressedDispatcher: OnBackPressedDispatcher? = null
    private val _backStackEntry: MutableList<NavigationStackEntry> = mutableListOf()
    private val _destinationState: MutableStateFlow<NavigationStackEntry?> = MutableStateFlow(null)

    val destinationState: CFlow<NavigationStackEntry?> = _destinationState.wrap()
    val backStackEntry: List<NavigationStackEntry> = _backStackEntry

    fun setupBackPressedDispatcher(onBackPressedDispatcher: OnBackPressedDispatcher) {
        _onBackPressedDispatcher = onBackPressedDispatcher
        _onBackPressedDispatcher?.backPressedCallback = object : BackPressedCallback() {
            override fun onBackPressed() {
                popBackStack()
            }
        }
    }

    fun launch(screen: NavigationScreen, params: Any? = null) {
        launch(NavigationStackEntry(screen = screen, params = params))
    }

    fun launch(
        screen: NavigationScreen, params: Any? = null,
        navigationParamsBuilder: NavigationParamsBuilder.() -> Unit
    ) {
        launch(NavigationStackEntry(screen = screen, params = params), navigationParamsBuilder)
    }

    fun launch(stackEntry: NavigationStackEntry) {
        childRoot = _childrenRootControllers[stackEntry.screen.toString()]

        _backStackEntry.add(stackEntry)
        _destinationState.value = stackEntry

        println("Backstack $debugName current -> $_backStackEntry")
    }

    fun launch(
        stackEntry: NavigationStackEntry,
        navigationParamsBuilder: NavigationParamsBuilder.() -> Unit
    ) {
        childRoot = _childrenRootControllers[stackEntry.screen.toString()]

        val navigationParams = NavigationParamsBuilder().apply(navigationParamsBuilder).build()
        if (navigationParams.launchFlags.contains(LaunchFlag.SingleTop) && navigationParams.launchFlags.contains(
                LaunchFlag.NewSingleTask
            )
        ) {
            throw IllegalStateException("Can't have both LaunchFlag.SingleTop and LaunchFlag.NewSingleTask")
        }

        if (navigationParams.launchFlags.contains(LaunchFlag.SingleTop)) {
            val foundIndex = _backStackEntry.indexOfFirst { it.screen == stackEntry.screen }
            if (foundIndex == -1) {
                launch(stackEntry = stackEntry)
                return
            }

            val foundElement = _backStackEntry[foundIndex]

            _backStackEntry.removeAt(foundIndex)
            _backStackEntry.add(foundElement)
            _destinationState.value = foundElement

            println("Backstack $debugName current -> $_backStackEntry")
            return
        }

        if (navigationParams.launchFlags.contains(LaunchFlag.NewSingleTask)) {
            val foundIndex = _backStackEntry.indexOfFirst { it.screen == stackEntry.screen }

            _backStackEntry.removeAt(foundIndex)
            _backStackEntry.add(stackEntry)
            _destinationState.value = stackEntry

            println("Backstack $debugName current -> $_backStackEntry")
            return
        }

        println("Backstack $debugName current -> $_backStackEntry")
    }

    fun newChildRootInstance(screen: NavigationScreen): RootController {
        val rootController = RootController()
        rootController.parentRoot = this
        rootController.debugName = screen.toString()
        _childrenRootControllers[screen.toString()] = rootController
        return rootController
    }

    fun popBackStack(): Boolean {
        var currentChild = childRoot
        println("Backstack $debugName Current child before -> ${currentChild?.debugName}")
        while (currentChild != null) {
            if (currentChild.popBackStack()) {
                return true
            } else {
                currentChild = childRoot?.childRoot
            }
        }

        println("Backstack $debugName Current child after -> ${currentChild?.debugName}")
        println("Backstack $debugName Size -> $_backStackEntry")

        if (currentChild == childRoot && _backStackEntry.isEmpty()) {
            return false
        }

        println("Backstack $debugName return false")

        _backStackEntry.removeLast()
        if (_backStackEntry.isEmpty()) return false

        _destinationState.value = backStackEntry.last()
        return true
    }
}