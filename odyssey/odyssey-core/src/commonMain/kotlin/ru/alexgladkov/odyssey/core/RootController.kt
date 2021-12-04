package ru.alexgladkov.odyssey.core

import kotlinx.coroutines.flow.MutableStateFlow
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.animations.defaultFadeAnimation
import ru.alexgladkov.odyssey.core.animations.defaultPresentationAnimation
import ru.alexgladkov.odyssey.core.animations.defaultPushAnimation
import ru.alexgladkov.odyssey.core.backpress.BackPressedCallback
import ru.alexgladkov.odyssey.core.backpress.OnBackPressedDispatcher
import ru.alexgladkov.odyssey.core.controllers.FlowRootController
import ru.alexgladkov.odyssey.core.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.core.declarative.RootControllerBuilder
import ru.alexgladkov.odyssey.core.destination.*
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

    /** Use this to get current back stack */
    var backStack: List<NavigationEntry> = _backStack

    /**
     * Debug name need to debug :) if you like console debugging
     * Setup automatically
     */
    var debugName: String? = if (parentRootController == null) "Root" else null
        protected set

    /**
     * Set navigation graph to RootController
     * @param block - declarative list of destinations
     * @see Destination to know more
     */
    fun setNavigationGraph(block: RootControllerBuilder.() -> Unit) {
        _allowedDestinations.clear()
        _allowedDestinations.addAll(RootControllerBuilder().apply(block).build())
    }

    /**
     * Set allowed destinations to RootController
     * @param list - list of destinations
     * @see Destination to know more
     */
    fun setNavigationGraph(list: List<Destination>) {
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
     * @param animationType - preferred animationType
     * @param launchFlag - flag if you want to change default behavior @see LaunchFlag
     */
    fun launch(
        screen: String, params: Any? = null,
        animationType: AnimationType = AnimationType.None,
        launchFlag: LaunchFlag? = null
    ) {
        launch(
            destinationScreen = DestinationScreen(name = screen, params = params),
            animationType = animationType,
            launchFlag = launchFlag
        )
    }

    /**
     * Helper function to draw screen with presentation (aka modal) style
     * @param screen - screen code, for example "splash". Must be included
     * in navigation graph or will cause an error
     * @param params - any bunch of params you need for the screen
     * @param launchFlag - flag if you want to change default behavior @see LaunchFlag
     */
    fun present(
        screen: String, params: Any? = null,
        launchFlag: LaunchFlag? = null
    ) {
        launch(
            destinationScreen = DestinationScreen(name = screen, params = params),
            animationType = defaultPresentationAnimation(),
            launchFlag = launchFlag
        )
    }

    /**
     * Helper function to draw screen with push style (inner navigation)
     * @param screen - screen code, for example "splash". Must be included
     * in navigation graph or will cause an error
     * @param params - any bunch of params you need for the screen
     * @param launchFlag - flag if you want to change default behavior @see LaunchFlag
     */
    fun push(
        screen: String, params: Any? = null,
        launchFlag: LaunchFlag? = null
    ) {
        launch(
            destinationScreen = DestinationScreen(name = screen, params = params),
            animationType = defaultPushAnimation(),
            launchFlag = launchFlag
        )
    }

    /**
     * Helper function to draw screen with crossfade style
     * @param screen - screen code, for example "splash". Must be included
     * in navigation graph or will cause an error
     * @param params - any bunch of params you need for the screen
     * @param launchFlag - flag if you want to change default behavior @see LaunchFlag
     */
    fun fade(
        screen: String, params: Any? = null,
        launchFlag: LaunchFlag? = null
    ) {
        launch(
            destinationScreen = DestinationScreen(name = screen, params = params),
            animationType = defaultFadeAnimation(),
            launchFlag = launchFlag
        )
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

            val last = _backStack.removeLast()
            val lastDestination = _backStack.last().destination
                .mapToNavigationEntry(_backStack.last().rootController, last.animationType)
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

    private fun launch(
        destinationScreen: DestinationScreen,
        animationType: AnimationType,
        launchFlag: LaunchFlag? = null
    ) {
        when (launchFlag) {
            LaunchFlag.SingleNewTask -> _backStack.clear()
        }

        val destination = _allowedDestinations.firstOrNull { point ->
            point.destinationName() == destinationScreen.name
        }

        when (destination) {
            is DestinationFlow -> createFlowAndPresent(destination, animationType, destinationScreen.params)
            is DestinationMultiFlow -> createMultiFlowAndPresent(destination, animationType)
            is DestinationScreen -> present(
                destinationPoint = DestinationPoint(
                    destinationScreen = destinationScreen,
                    animationType = animationType,
                    rootController = this
                )
            )
        }
    }

    fun popTo(
        screen: String, params: Any? = null,
        animationType: AnimationType = AnimationType.None,
        launchFlag: LaunchFlag? = null
    ) {
        popTo(
            destinationScreen = DestinationScreen(name = screen, params = params),
            animationType = animationType,
            launchFlag = launchFlag,
        )
    }

    private fun popTo(
        destinationScreen: DestinationScreen,
        animationType: AnimationType,
        launchFlag: LaunchFlag? = null
    ) {
        when (launchFlag) {
            LaunchFlag.SingleNewTask -> _backStack.clear()
        }

        val destination = _allowedDestinations.firstOrNull { point ->
            point.destinationName() == destinationScreen.name
        }

        when (destination) {
            is DestinationFlow -> createFlowAndDelete(
                destination,
                animationType,
                destinationScreen.params
            )
            is DestinationMultiFlow -> createMultiFlowAndDelete(destination, animationType)
            is DestinationScreen -> delete(
                destinationPoint = DestinationPoint(
                    destinationScreen = destinationScreen,
                    animationType = animationType,
                    rootController = this
                )
            )
        }

    }

    private fun createFlowAndPresent(
        destinationFlow: DestinationFlow,
        animationType: AnimationType,
        params: Any? = null
    ) {
        val firstDestination = destinationFlow.screens.first()
        val flowRootController = FlowRootController(screenHost)
        flowRootController.parentRootController = this
        flowRootController.debugName = destinationFlow.name
        flowRootController.setNavigationGraph(destinationFlow.screens)

        val firstNavigationEntry = firstDestination.mapToNavigationEntry(flowRootController, animationType)
        flowRootController._backStack.add(firstNavigationEntry)
        flowRootController._backStackObserver.tryEmit(firstNavigationEntry)

        val navigationEntry = destinationFlow.copy(params = params).mapToNavigationEntry(flowRootController, animationType)
        _backStack.add(navigationEntry)
        _backStackObserver.tryEmit(navigationEntry)
    }


    private fun createFlowAndDelete(
        destinationFlow: DestinationFlow,
        animationType: AnimationType,
        params: Any? = null
    ) {
        val firstDestination = destinationFlow.screens.first()
        val flowRootController = FlowRootController(screenHost)
        flowRootController.parentRootController = this
        flowRootController.debugName = destinationFlow.name
        flowRootController.setNavigationGraph(destinationFlow.screens)

        val firstNavigationEntry =
            firstDestination.mapToNavigationEntry(flowRootController, animationType)
        flowRootController._backStack.add(firstNavigationEntry)
        flowRootController._backStackObserver.tryEmit(firstNavigationEntry)

        val navigationEntry = destinationFlow.copy(params = params)
            .mapToNavigationEntry(flowRootController, animationType)
        _backStack.remove(navigationEntry)
    }

    private fun createMultiFlowAndPresent(destinationMultiFlow: DestinationMultiFlow, animationType: AnimationType) {
        val multiStackRootController = MultiStackRootController(screenHost)
        multiStackRootController.parentRootController = this
        multiStackRootController.debugName = destinationMultiFlow.name
        multiStackRootController.setNavigationGraph(destinationMultiFlow.flows)

        destinationMultiFlow.flows.forEach { destinationFlow ->
            val firstDestination = destinationFlow.screens.first()
            val flowRootController = FlowRootController(screenHost)
            flowRootController.debugName = destinationFlow.name
            flowRootController.setNavigationGraph(destinationFlow.screens)

            val firstNavEntry = firstDestination.mapToNavigationEntry(flowRootController, AnimationType.None)
            multiStackRootController.childrenRootController.add(flowRootController)
            flowRootController.parentRootController = multiStackRootController
            flowRootController._backStack.add(firstNavEntry)
            flowRootController._backStackObserver.tryEmit(firstNavEntry)
        }

        val multiStackNavigationEntry = destinationMultiFlow.mapToNavigationEntry(multiStackRootController, animationType)
        _backStack.add(multiStackNavigationEntry)
        _backStackObserver.tryEmit(multiStackNavigationEntry)

        val firstFlowNavigationEntry = destinationMultiFlow.flows.first()
            .mapToNavigationEntry(multiStackRootController.childrenRootController.first(), animationType)
        multiStackRootController._backStack.add(firstFlowNavigationEntry)
        multiStackRootController._backStackObserver.tryEmit(firstFlowNavigationEntry)
    }

    private fun createMultiFlowAndDelete(
        destinationMultiFlow: DestinationMultiFlow,
        animationType: AnimationType
    ) {
        val multiStackRootController = MultiStackRootController(screenHost)
        multiStackRootController.parentRootController = this
        multiStackRootController.debugName = destinationMultiFlow.name
        multiStackRootController.setNavigationGraph(destinationMultiFlow.flows)

        destinationMultiFlow.flows.forEach { destinationFlow ->
            val firstDestination = destinationFlow.screens.first()
            val flowRootController = FlowRootController(screenHost)
            flowRootController.debugName = destinationFlow.name
            flowRootController.setNavigationGraph(destinationFlow.screens)

            val firstNavEntry =
                firstDestination.mapToNavigationEntry(flowRootController, AnimationType.None)
            multiStackRootController.childrenRootController.add(flowRootController)
            flowRootController.parentRootController = multiStackRootController
            flowRootController._backStack.remove(firstNavEntry)
        }

        val multiStackNavigationEntry = destinationMultiFlow.mapToNavigationEntry(multiStackRootController, animationType)
        _backStack.remove(multiStackNavigationEntry)

        val firstFlowNavigationEntry = destinationMultiFlow.flows.first()
            .mapToNavigationEntry(
                multiStackRootController.childrenRootController.first(),
                animationType
            )
        multiStackRootController._backStack.remove(firstFlowNavigationEntry)
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

    protected open fun delete(destinationPoint: DestinationPoint) {
        val navigationEntry = destinationPoint.mapToNavigationEntry()
        _backStack.remove(navigationEntry)
        if (_backStack.isEmpty()) {
            // Start drawing process when first screen passed
            screenHost.draw(destinationPoint)
        }
    }
}