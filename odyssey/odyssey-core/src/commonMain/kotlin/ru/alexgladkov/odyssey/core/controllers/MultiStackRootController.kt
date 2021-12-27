package ru.alexgladkov.odyssey.core.controllers

//class MultiStackRootController(screenHost: ScreenHost) : RootController(screenHost) {
//
//    var childrenRootController: MutableList<RootController> = mutableListOf()
//    var currentPosition = 0
//        private set
//
//    fun switchFlow(position: Int, rootController: RootController) {
//        val destination = _allowedDestinations[position]
//        currentPosition = position
//        _backStackObserver.tryEmit(destination.mapToNavigationEntry(rootController, AnimationType.None))
//    }
//
//    override fun present(destinationPoint: DestinationPoint) {
//        val navigationEntry = destinationPoint.mapToNavigationEntry()
//        _backStack.add(navigationEntry)
//        _backStackObserver.tryEmit(navigationEntry)
//    }
//
//    override fun popBackStack(): Boolean {
//        return childrenRootController[currentPosition].popBackStack()
//    }
//}