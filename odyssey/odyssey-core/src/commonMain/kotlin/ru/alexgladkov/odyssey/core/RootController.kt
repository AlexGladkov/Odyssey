package ru.alexgladkov.odyssey.core

import kotlinx.coroutines.flow.MutableStateFlow
import ru.alexgladkov.odyssey.core.backpress.BackPressedCallback
import ru.alexgladkov.odyssey.core.backpress.OnBackPressedDispatcher
import ru.alexgladkov.odyssey.core.controllers.FlowRootController
import ru.alexgladkov.odyssey.core.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.core.destination.*
import ru.alexgladkov.odyssey.core.destination.DestinationPoint
import ru.alexgladkov.odyssey.core.destination.mapToNavigationEntry
import ru.alexgladkov.odyssey.core.extensions.CFlow
import ru.alexgladkov.odyssey.core.extensions.wrap

/**
 * This is very base class for library
 * RootController handle backstack, work with back press,
 * can navigate through flows, multistack and provides observe on backstack
 *
 * @param screenHost - canvas for drawing
 * @see ScreenHost for more information
 */
open class RootController(var screenHost: ScreenHost) {

    var parentRootController: RootController? = null
    var onApplicationFinish: (() -> Unit)? = null

    protected val _backStack: MutableList<NavigationEntry> = mutableListOf()
    protected val _backStackObserver: MutableStateFlow<NavigationEntry?> = MutableStateFlow(null)
    protected val _allowedDestinations: MutableList<Destination> = mutableListOf()
    private var _onBackPressedDispatcher: OnBackPressedDispatcher? = null

    /** Use this to control backstack changing */
    var backStackObserver: CFlow<NavigationEntry?> = _backStackObserver.wrap()

    /**
     * Debug name need to debug :) if you like console debugging
     * Setup automatically
     */
    var debugName: String? = if (parentRootController == null) "Root" else null
        protected set

    /**
     * Set navigation graph to RootController
     * @param list - list of destinations
     * @see Destination to know more
     */
    // TODO Replace it to declarative creation
    fun generateDestinations(list: List<Destination>) {
        _allowedDestinations.clear()
        _allowedDestinations.addAll(list)
    }

    /**
     * Send command to controller to launch new scenario
     * Under the hood library check navigation graph and create simple screen,
     * flow or multistack flow
     * @param screen - screen code, for example "splash". Must be included
     * in navigation graph or will cause an error
     * @param params - any bunch of params you need for the screen
     * @param launchFlag - flag if you want to change default behavior @see LaunchFlag
     */
    fun launch(screen: String, params: Any? = null, launchFlag: LaunchFlag? = null) {
        launch(DestinationScreen(name = screen, params = params), launchFlag)
    }

    /**
     * Remove the latest navigation stack entry and draw it on canvas
     */
    open fun popBackStack(): Boolean {
        val isBackPressed = when (val childRootController = _backStack.last().rootController) {
            is MultiStackRootController -> childRootController.popBackStack()
            is FlowRootController -> childRootController.popBackStack()
            else -> false
        }

        if (!isBackPressed) {
            if (_backStack.size <= 1) {
                return false
            }

            _backStack.removeLast()
            val lastDestination = _backStack.last().destination.mapToNavigationEntry(_backStack.last().rootController)
            _backStackObserver.tryEmit(lastDestination)
        }

        return true
    }

    /**
     * Call this to work with back press mechanism
     * You can use it with android to handle hardware back press
     * or can provide custom implementation
     */
    fun setupBackPressedDispatcher(onBackPressedDispatcher: OnBackPressedDispatcher) {
        _onBackPressedDispatcher = onBackPressedDispatcher
        _onBackPressedDispatcher?.backPressedCallback = object : BackPressedCallback() {
            override fun onBackPressed() {
                if (!popBackStack()) {
                    onApplicationFinish?.invoke()
                }
            }
        }
    }

    private fun launch(destinationScreen: DestinationScreen, launchFlag: LaunchFlag? = null) {
        when (launchFlag) {
            LaunchFlag.SingleNewTask -> _backStack.clear()
        }

        val destination = _allowedDestinations.firstOrNull { point ->
            point.destinationName() == destinationScreen.name
        }

        when (destination) {
            is DestinationFlow -> createFlowAndPresent(destination)
            is DestinationMultiFlow -> createMultiFlowAndPresent(destination)
            is DestinationScreen -> present(destinationPoint = DestinationPoint(destinationScreen, this))
        }
    }

    private fun createFlowAndPresent(destinationFlow: DestinationFlow) {
        val firstDestination = destinationFlow.screens.first()
        val flowRootController = FlowRootController(screenHost)
        flowRootController.parentRootController = this
        flowRootController.debugName = destinationFlow.name
        flowRootController.generateDestinations(destinationFlow.screens)

        val firstNavigationEntry = firstDestination.mapToNavigationEntry(flowRootController)
        flowRootController._backStack.add(firstNavigationEntry)
        flowRootController._backStackObserver.tryEmit(firstNavigationEntry)

        val navigationEntry = destinationFlow.mapToNavigationEntry(flowRootController)
        _backStack.add(navigationEntry)
        _backStackObserver.tryEmit(navigationEntry)
    }

    private fun createMultiFlowAndPresent(destinationMultiFlow: DestinationMultiFlow) {
        val multiStackRootController = MultiStackRootController(screenHost)
        multiStackRootController.parentRootController = this
        multiStackRootController.debugName = destinationMultiFlow.name
        multiStackRootController.generateDestinations(destinationMultiFlow.flows)

        destinationMultiFlow.flows.forEach { destinationFlow ->
            val firstDestination = destinationFlow.screens.first()
            val flowRootController = FlowRootController(screenHost)
            flowRootController.debugName = destinationFlow.name
            flowRootController.generateDestinations(destinationFlow.screens)

            val firstNavEntry = firstDestination.mapToNavigationEntry(flowRootController)
            multiStackRootController.childrenRootController.add(flowRootController)
            flowRootController.parentRootController = multiStackRootController
            flowRootController._backStack.add(firstNavEntry)
            flowRootController._backStackObserver.tryEmit(firstNavEntry)
        }

        val multiStackNavigationEntry = destinationMultiFlow.mapToNavigationEntry(multiStackRootController)
        _backStack.add(multiStackNavigationEntry)
        _backStackObserver.tryEmit(multiStackNavigationEntry)

        val firstFlowNavigationEntry = destinationMultiFlow.flows.first()
            .mapToNavigationEntry(multiStackRootController.childrenRootController.first())
        multiStackRootController._backStack.add(firstFlowNavigationEntry)
        multiStackRootController._backStackObserver.tryEmit(firstFlowNavigationEntry)
    }

    protected open fun present(destinationPoint: DestinationPoint) {
        val navigationEntry = destinationPoint.mapToNavigationEntry()

        if (_backStack.isEmpty()) {
            // Start drawing process when first screen passed
            screenHost.draw(destinationPoint)
        }

        _backStack.add(navigationEntry)
        _backStackObserver.tryEmit(navigationEntry)
    }
}