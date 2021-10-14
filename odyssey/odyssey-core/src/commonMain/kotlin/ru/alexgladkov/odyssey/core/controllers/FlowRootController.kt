package ru.alexgladkov.odyssey.core.controllers

import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.ScreenHost
import ru.alexgladkov.odyssey.core.destination.DestinationPoint
import ru.alexgladkov.odyssey.core.destination.mapToNavigationEntry

class FlowRootController(screenHost: ScreenHost) : RootController(screenHost) {

    override fun present(destinationPoint: DestinationPoint) {
        val navigationEntry = destinationPoint.mapToNavigationEntry()
        _backStack.add(navigationEntry)
        _backStackObserver.tryEmit(navigationEntry)
    }

    override fun popBackStack() : Boolean {
        if (_backStack.size <= 1) {
            return false
        }

        _backStack.removeLast()
        _backStackObserver.tryEmit(_backStack.last().destination.mapToNavigationEntry(this))
        return true
    }
}