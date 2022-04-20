package ru.alexgladkov.odyssey.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.alexgladkov.odyssey.compose.base.BottomBarNavigator
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.base.TopBarNavigator
import ru.alexgladkov.odyssey.compose.controllers.ModalController
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.compose.controllers.TabNavigationModel
import ru.alexgladkov.odyssey.compose.extensions.createUniqueKey
import ru.alexgladkov.odyssey.compose.helpers.*
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.*
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.NavConfiguration
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.backpress.BackPressedCallback
import ru.alexgladkov.odyssey.core.backpress.OnBackPressedDispatcher
import ru.alexgladkov.odyssey.core.screen.Screen
import ru.alexgladkov.odyssey.core.screen.ScreenBundle
import ru.alexgladkov.odyssey.core.wrap
import kotlin.collections.HashMap

typealias RenderWithParams<T> = @Composable (T) -> Unit
typealias Render = @Composable () -> Unit

sealed class ScreenType {
    object Simple : ScreenType()
    data class Flow(val flowBuilderModel: FlowBuilderModel) : ScreenType()
    data class MultiStack<Cfg : TabsNavConfiguration>(
        val multiStackBuilderModel: MultiStackBuilderModel,
        val tabsNavModel: TabsNavModel<Cfg>
    ) : ScreenType()
}

data class AllowedDestination(
    val key: String,
    val screenType: ScreenType
)

open class RootController(
    private val rootControllerType: RootControllerType = RootControllerType.Root
) {
    private val _allowedDestinations: MutableList<AllowedDestination> = mutableListOf()
    private val _backstack = mutableListOf<Screen>()
    private val _currentScreen: MutableStateFlow<NavConfiguration> =
        MutableStateFlow(Screen().wrap())
    private var _childrenRootController: MutableList<RootController> = mutableListOf()
    private val _screenMap = mutableMapOf<String, RenderWithParams<Any?>>()
    private var _onBackPressedDispatcher: OnBackPressedDispatcher? = null
    private var _modalController: ModalController? = null
    private var _deepLinkUri: String? = null

    var parentRootController: RootController? = null
    var backgroundColor: Color = Color.White
    var onApplicationFinish: (() -> Unit)? = null
    var onScreenRemove: (ScreenBundle) -> Unit =
        { parentRootController?.onScreenRemove?.invoke(it) }

    var currentScreen: StateFlow<NavConfiguration> = _currentScreen.asStateFlow()

    /**
     * Debug name need to debug :) if you like console debugging
     * Setup automatically
     */
    var debugName: String? = if (parentRootController == null) "Root" else null
        private set

    init {
        initServiceScreens()
    }

    // Get screen render compose function
    fun getScreenRender(screenName: String?): RenderWithParams<Any?>? {
        return _screenMap[screenName]
    }

    // Render screen with params
    @Composable
    fun RenderScreen(screenName: String?, params: Any?) {
        _screenMap[screenName]?.invoke(params)
    }

    /**
     * Update root controller screen map to find composables
     * @param screenMap - generated screen map
     */
    fun updateScreenMap(screenMap: HashMap<String, RenderWithParams<Any?>>) {
        _screenMap.putAll(screenMap)
    }

    /**
     * Set allowed destinations to RootController
     * @param list - list of destinations
     * @see Destination to know more
     */
    fun setNavigationGraph(list: List<AllowedDestination>) {
        _allowedDestinations.clear()
        _allowedDestinations.addAll(list)
    }

    /**
     * Call this to work with back press mechanism
     * You can use it with android to handle hardware back press
     * or can provide custom implementation
     */
    fun setupBackPressedDispatcher(onBackPressedDispatcher: OnBackPressedDispatcher?) {
        _onBackPressedDispatcher = onBackPressedDispatcher
        _onBackPressedDispatcher?.backPressedCallback = object : BackPressedCallback() {
            override fun onBackPressed() {
                println("DEBUG: On Back Pressed")
                popBackStack()
            }
        }
    }

    /** Measure deep of root controller */
    fun measureLevel(): Int = findRootController()._backstack.size


    /**
     * Send command to controller to launch new scenario
     * Under the hood library check navigation graph and create simple screen,
     * flow or multistack flow
     * @param screen - screen code, for example "splash". Must be included
     * in navigation graph or will cause an error
     * @param startScreen - start screen for flow/multistack
     * @param startTabPosition - start tab position for multistack
     * @param params - any bunch of params you need for the screen
     * @param animationType - preferred animationType
     * @param launchFlag - flag if you want to change default behavior @see LaunchFlag
     */
    fun launch(
        screen: String,
        startScreen: String? = null,
        startTabPosition: Int = 0,
        params: Any? = null,
        animationType: AnimationType = AnimationType.None,
        launchFlag: LaunchFlag? = null,
        deepLink: Boolean = false
    ) {
        if (deepLink && _deepLinkUri?.isNotBlank() == true) {
            proceedDeepLink(animationType = animationType, launchFlag = launchFlag)
            return
        }

        val screenType = _allowedDestinations.find { it.key == screen }?.screenType
            ?: throw IllegalStateException("Can't find screen in destination. Did you provide this screen?")

        when (screenType) {
            is ScreenType.Flow -> launchFlowScreen(
                screen,
                startScreen,
                params,
                animationType,
                screenType.flowBuilderModel,
                launchFlag
            )

            is ScreenType.Simple -> launchSimpleScreen(screen, params, animationType, launchFlag)
            is ScreenType.MultiStack<*> -> launchMultiStackScreen(
                animationType = animationType,
                multiStackBuilderModel = screenType.multiStackBuilderModel,
                tabsNavModel = screenType.tabsNavModel,
                launchFlag = launchFlag,
                startScreen = startScreen,
                startTabPosition = startTabPosition
            )
        }
    }

    // Returns to previous screen
    open fun popBackStack() {
        if (_modalController?.isEmpty() == false) {
            _modalController?.popBackStack()
            return
        }

        when (_backstack.last().realKey) {
            flowKey -> removeTopScreen(_childrenRootController.last())
            multiStackKey -> _childrenRootController.last().popBackStack()
            else -> removeTopScreen(this)
        }
    }

    /**
     * back to  the first screen by name recursively
     */
    fun backToScreen(screenName: String) {
        _modalController?.clearBackStack()

        when (_backstack.last().key) {
            flowKey -> backToScreen(_childrenRootController.last(), screenName)
            multiStackKey -> backToScreen(_childrenRootController.last(), screenName)
            else -> backToScreen(this, screenName)
        }
    }

    //Find first RootController in hierarchy
    fun findRootController(): RootController {
        var currentRootController = this
        while (currentRootController.parentRootController != null) {
            currentRootController = currentRootController.parentRootController!!
        }

        return currentRootController
    }

    // Returns controller to show modal sheets
    fun findModalController(): ModalController {
        return if (_modalController == null) {
            findRootController()._modalController!!
        } else {
            _modalController!!
        }
    }

    @Deprecated("@see findModalController", ReplaceWith("findModalController()"))
    fun findModalSheetController() = findModalController()

    /**
     * Attaches Modal Controller to Root Controller
     * @param modalController - controller to show modal sheets
     */
    fun attachModalController(modalController: ModalController) {
        this._modalController = modalController
    }

    /**
     * Draws current screen in stack (need for Navigator)
     * @param startScreen - draw start screen for flow/multistack
     * @param startParams - param for startScreen in flow
     */
    fun drawCurrentScreen(startScreen: String? = null, startParams: Any? = null) {
        if (_backstack.isEmpty()) {
            launch(
                screen = _allowedDestinations.firstOrNull { it.key == startScreen }?.key
                    ?: _allowedDestinations.first().key,
                params = startParams
            )
        } else {
            val current = _backstack.last()
            _currentScreen.value = current.copy(animationType = AnimationType.None).wrap()
        }
    }

    /**
     * @param path - uri path
     *  Set information about deeplink
     */
    fun setDeepLinkUri(path: String?) {
        this._deepLinkUri = path
    }

    private fun proceedDeepLink(animationType: AnimationType, launchFlag: LaunchFlag?) {
        val splitDeepLink = _deepLinkUri?.split("/") ?: emptyList()
        if (splitDeepLink.size < 2) {
            throw IllegalStateException("Deeplink $_deepLinkUri has illegal format")
        }

        val searchKey = splitDeepLink[1]
        val params = if (splitDeepLink.size > 2) splitDeepLink[2] else null
        var startTabPosition = 0

        val destination = _allowedDestinations.firstOrNull { destination ->
            when (val screen = destination.screenType) {
                ScreenType.Simple -> searchKey == destination.key
                is ScreenType.Flow -> screen.flowBuilderModel.allowedDestination.firstOrNull { it.key == searchKey } != null
                is ScreenType.MultiStack<*> -> {
                    var containsScreen = false
                    run loop@{
                        screen.multiStackBuilderModel.tabItems.forEachIndexed { index, info ->
                            containsScreen =
                                info.allowedDestination.firstOrNull { it.key == searchKey } != null
                            if (containsScreen) {
                                startTabPosition = index
                                return@loop
                            }
                        }
                    }

                    containsScreen
                }
            }
        }

        destination?.let {
            launch(
                screen = destination.key,
                startScreen = searchKey,
                startTabPosition = startTabPosition,
                params = params,
                animationType = animationType,
                launchFlag = launchFlag,
                deepLink = false
            )
        } ?: throw IllegalStateException("Can't launch $_deepLinkUri to unknown screen")

        _deepLinkUri = null
    }

    private fun backToScreen(rootController: RootController?, screenName: String) {
        rootController?.let {
            val isLastScreen = it._backstack.size <= 1
            if (isLastScreen) {
                if (it.debugName == "Root") {
                    it.onApplicationFinish?.invoke()
                } else {
                    val last = it._backstack.removeLast()
                    val parentController = it.parentRootController ?: return
                    val current = parentController._backstack.last()
                    if (current.realKey == screenName) {
                        parentController._currentScreen.value = current
                            .copy(animationType = last.animationType, isForward = false)
                            .wrap(with = last)
                    } else {
                        backToScreen(it.parentRootController, screenName)
                    }
                }
            } else {
                val last = it._backstack.removeLast()
                val current = it._backstack.last()
                if (current.realKey == screenName) {
                    it._currentScreen.value = current
                        .copy(animationType = last.animationType, isForward = false)
                        .wrap(with = last)
                } else {
                    backToScreen(rootController, screenName)
                }
            }
        }
    }

    private fun removeTopScreen(rootController: RootController?) {
        println("RC ${rootController?.debugName}")
        rootController?.let {
            val isLastScreen = it._backstack.size <= 1
            if (isLastScreen) {
                if (it.debugName == "Root") {
                    it.onApplicationFinish?.invoke()
                } else {
                    removeTopScreen(it.parentRootController)
                    it.parentRootController?._childrenRootController?.removeLast()
                }
            } else {
                val last = it._backstack.removeLast()
                val current = it._backstack.last()

                it._currentScreen.value = current
                    .copy(animationType = last.animationType, isForward = false)
                    .wrap(with = last)
            }
        }
    }

    private fun launchSimpleScreen(
        key: String,
        params: Any?,
        animationType: AnimationType,
        launchFlag: LaunchFlag?
    ) {
        val screen = if (_backstack.isEmpty() && launchFlag == null) {
            Screen(key = randomizeKey(key), realKey = key, params = params)
        } else {
            Screen(
                key = randomizeKey(key),
                realKey = key,
                params = params,
                animationType = animationType
            )
        }

        when (launchFlag) {
            LaunchFlag.SingleNewTask -> _backstack.clear()
        }

        _backstack.add(screen)
        _currentScreen.value = screen.wrap()
    }

    private fun launchFlowScreen(
        key: String,
        startScreen: String?,
        params: Any?,
        animationType: AnimationType,
        flowBuilderModel: FlowBuilderModel,
        launchFlag: LaunchFlag?
    ) {
        if (rootControllerType == RootControllerType.Flow) throw IllegalStateException("Don't use flow inside flow, call findRootController() instead")

        when (launchFlag) {
            LaunchFlag.SingleNewTask -> _backstack.clear()
        }

        val rootController = RootController(RootControllerType.Flow)
        rootController.backgroundColor = backgroundColor

        rootController.debugName = key
        rootController.parentRootController = this
        rootController.onApplicationFinish = {
            rootController.parentRootController?.popBackStack()
        }

        rootController.setDeepLinkUri(_deepLinkUri)
        rootController.updateScreenMap(flowBuilderModel.screenMap)
        rootController.setNavigationGraph(flowBuilderModel.allowedDestination)
        _childrenRootController.add(rootController)

        val targetScreen =
            flowBuilderModel.allowedDestination.firstOrNull { startScreen == it.key }?.key
                ?: flowBuilderModel.allowedDestination.first().key

        val screen = Screen(
            key = randomizeKey(flowKey),
            realKey = flowKey,
            animationType = animationType,
            params = FlowBundle(
                key = targetScreen,
                startScreen = targetScreen,
                params = params,
                rootController = rootController
            )
        )

        _backstack.add(screen)
        _currentScreen.value = screen.wrap()
    }

    private fun launchMultiStackScreen(
        animationType: AnimationType,
        multiStackBuilderModel: MultiStackBuilderModel,
        tabsNavModel: TabsNavModel<*>,
        startScreen: String? = null,
        startTabPosition: Int = 0,
        launchFlag: LaunchFlag?
    ) {
        if (rootControllerType == RootControllerType.Flow || rootControllerType == RootControllerType.MultiStack)
            throw IllegalStateException("Don't use flow inside flow, call findRootController instead")

        when (launchFlag) {
            LaunchFlag.SingleNewTask -> _backstack.clear()
        }

        val parentRootController = this
        val configurations = multiStackBuilderModel.tabItems.map {
            val rootController = RootController(RootControllerType.Tab)
            rootController.backgroundColor = backgroundColor
            rootController.parentRootController = parentRootController
            rootController.debugName = it.tabItem.name
            rootController.setDeepLinkUri(_deepLinkUri)
            rootController.updateScreenMap(it.screenMap)
            rootController.setNavigationGraph(it.allowedDestination)
            TabNavigationModel(tabInfo = it, rootController = rootController)
        }

        val rootController = MultiStackRootController(
            rootControllerType = RootControllerType.MultiStack,
            tabsNavModel = tabsNavModel,
            tabItems = configurations,
            startTabPosition = startTabPosition
        )

        rootController.setDeepLinkUri(_deepLinkUri)
        _childrenRootController.add(rootController)

        val screen = Screen(
            key = multiStackKey,
            realKey = multiStackKey,
            animationType = animationType,
            params = MultiStackBundle(
                rootController = rootController,
                startScreen = startScreen
            )
        )

        _backstack.add(screen)
        _currentScreen.value = screen.wrap()
    }

    private fun initServiceScreens() {
        if (rootControllerType == RootControllerType.Root) {
            _screenMap[flowKey] = {
                val bundle = it as FlowBundle
                CompositionLocalProvider(
                    LocalRootController provides bundle.rootController
                ) {
                    Navigator(startScreen = bundle.startScreen, startParams = bundle.params)
                }
            }

            _screenMap[multiStackKey] = {
                val bundle = it as MultiStackBundle
                CompositionLocalProvider(
                    LocalRootController provides bundle.rootController
                ) {
                    when (bundle.rootController.tabsNavModel.navConfiguration.type) {
                        TabsNavType.Bottom -> {
                            BottomBarNavigator(
                                startScreen = bundle.startScreen
                            )
                        }
                        TabsNavType.Top -> {
                            TopBarNavigator(
                                startScreen = bundle.startScreen
                            )
                        }
                        TabsNavType.Custom -> {
                            val customNavConfiguration =
                                bundle.rootController.tabsNavModel.navConfiguration as CustomNavConfiguration
                            customNavConfiguration.content()
                        }
                    }
                }
            }
        }
    }

    private fun randomizeKey(key: String): String = createUniqueKey(key)

    companion object {
        private const val flowKey = "odyssey_flow_reserved_type"
        private const val multiStackKey = "odyssey_multi_stack_reserved_type"
    }
}