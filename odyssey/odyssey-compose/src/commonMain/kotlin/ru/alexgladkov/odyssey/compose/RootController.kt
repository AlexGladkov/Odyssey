package ru.alexgladkov.odyssey.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.alexgladkov.odyssey.compose.base.BottomBarNavigator
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.controllers.ModalSheetController
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.compose.controllers.TabNavigationModel
import ru.alexgladkov.odyssey.compose.extensions.createUniqueKey
import ru.alexgladkov.odyssey.compose.helpers.*
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.BottomNavModel
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.MultiStackBuilderModel
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.NavConfiguration
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.animations.defaultPresentationAnimation
import ru.alexgladkov.odyssey.core.animations.defaultPushAnimation
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
    data class MultiStack(val multiStackBuilderModel: MultiStackBuilderModel, val bottomNavModel: BottomNavModel) :
        ScreenType()
}

data class AllowedDestination(
    val key: String,
    val screenType: ScreenType
)

open class RootController(private val rootControllerType: RootControllerType = RootControllerType.Root) {
    private val _allowedDestinations: MutableList<AllowedDestination> = mutableListOf()
    private val _backstack = mutableListOf<Screen>()
    private val _currentScreen: MutableStateFlow<NavConfiguration> = MutableStateFlow(Screen().wrap())
    private var _childrenRootController: MutableList<RootController> = mutableListOf()
    private val _screenMap = mutableMapOf<String, RenderWithParams<Any?>>()
    private var _onBackPressedDispatcher: OnBackPressedDispatcher? = null
    private var _modalSheetController: ModalSheetController? = null
    private var _deepLinkUri: String? = null

    var parentRootController: RootController? = null
    var onApplicationFinish: (() -> Unit)? = null
    var onScreenRemove: (ScreenBundle) -> Unit = { parentRootController?.onScreenRemove?.invoke(it) }

    var currentScreen: StateFlow<NavConfiguration> = _currentScreen.asStateFlow()
    val screenMap = _screenMap

    /**
     * Debug name need to debug :) if you like console debugging
     * Setup automatically
     */
    var debugName: String? = if (parentRootController == null) "Root" else null
        private set

    init {
        initServiceScreens()
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
                popBackStack()
            }
        }
    }

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
        println("DEBUG launch $_deepLinkUri")
        if (deepLink && _deepLinkUri?.isNotBlank() == true) {
            proceedDeepLink(animationType = animationType, launchFlag = launchFlag)
            return
        }

        val screenType = _allowedDestinations.find { it.key == screen }?.screenType
            ?: throw IllegalStateException("Can't find screen in destination. Did you provide this screen?")

        println("DEBUG $screenType")
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
            is ScreenType.MultiStack -> launchMultiStackScreen(
                animationType = animationType,
                multiStackBuilderModel = screenType.multiStackBuilderModel,
                bottomNavModel = screenType.bottomNavModel,
                launchFlag = launchFlag,
                startScreen = startScreen,
                startTabPosition = startTabPosition
            )
        }
    }

    /**
     * Returns to previous screen
     */
    open fun popBackStack() {
        if (_modalSheetController?.isEmpty() == false) {
            _modalSheetController?.removeTopScreen()
            return
        }

        when (_backstack.last().key) {
            flowKey -> removeTopScreen(_childrenRootController.last())
            multiStackKey -> _childrenRootController.last().popBackStack()
            else -> removeTopScreen(this)
        }
    }

    /**
     * Find first RootController in hierarchy
     */
    fun findRootController(): RootController {
        var currentRootController = this
        while (currentRootController.parentRootController != null) {
            currentRootController = currentRootController.parentRootController!!
        }

        return currentRootController
    }

    /**
     * Returns controller to show modal sheets
     */
    fun findModalSheetController(): ModalSheetController {
        return if (_modalSheetController == null) {
            findRootController()._modalSheetController!!
        } else {
            _modalSheetController!!
        }
    }

    fun attachModalSheet(modalSheetController: ModalSheetController) {
        this._modalSheetController = modalSheetController
    }

    fun drawCurrentScreen(startScreen: String? = null, startParams: Any? = null) {
        if (_backstack.isEmpty()) {
            launch(
                screen = startScreen ?: _allowedDestinations.first().key,
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
        val splitDeepLink = _deepLinkUri?.split("/")
        if ((splitDeepLink?.size ?: 0) < 2) {
            throw IllegalStateException("Deeplink $_deepLinkUri has illegal format")
        }

        val searchKey = splitDeepLink?.get(1)
        val params = if ((splitDeepLink?.size ?: 0) > 2) splitDeepLink?.get(2) else null
        var startTabPosition = 0

        println("DEBUG search key $searchKey")
        searchKey?.let {
            val destination = _allowedDestinations.firstOrNull { destination ->
                when (val screen = destination.screenType) {
                    ScreenType.Simple -> searchKey == destination.key
                    is ScreenType.Flow -> screen.flowBuilderModel.allowedDestination.firstOrNull { it.key == searchKey } != null
                    is ScreenType.MultiStack -> {
                        var containsScreen = false
                        run loop@ {
                            screen.multiStackBuilderModel.tabItems.forEachIndexed { index, info ->
                                containsScreen = info.allowedDestination.firstOrNull { it.key == searchKey } != null
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

            println("DEBUG destination $destination, $searchKey")
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
        } ?: throw IllegalStateException("Can't launch $_deepLinkUri to unknown screen")
    }

    private fun removeTopScreen(rootController: RootController?) {
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

    private fun launchSimpleScreen(key: String, params: Any?, animationType: AnimationType, launchFlag: LaunchFlag?) {
        val screen = if (_backstack.isEmpty() && launchFlag == null) {
            Screen(key = randomizeKey(key), realKey = key, params = params)
        } else {
            Screen(key = randomizeKey(key), realKey = key, params = params, animationType = animationType)
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

        rootController.debugName = key
        rootController.parentRootController = this
        rootController.onApplicationFinish = {
            rootController.parentRootController?.popBackStack()
        }

        rootController.setDeepLinkUri(_deepLinkUri)
        rootController.updateScreenMap(flowBuilderModel.screenMap)
        rootController.setNavigationGraph(flowBuilderModel.allowedDestination)
        _childrenRootController.add(rootController)

        println("DEBUG ${flowBuilderModel.allowedDestination}")
        val targetScreen = flowBuilderModel.allowedDestination.firstOrNull { startScreen == it.key }?.key
            ?: flowBuilderModel.allowedDestination.first().key
        println("DEBUG $targetScreen")
        val screen = Screen(
            key = flowKey,
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
        bottomNavModel: BottomNavModel,
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
            rootController.parentRootController = parentRootController
            rootController.debugName = it.tabItem.name
            rootController.setDeepLinkUri(_deepLinkUri)
            rootController.updateScreenMap(it.screenMap)
            rootController.setNavigationGraph(it.allowedDestination)
            TabNavigationModel(tabInfo = it, rootController = rootController)
        }

        val rootController = MultiStackRootController(
            rootControllerType = RootControllerType.MultiStack,
            bottomNavModel = bottomNavModel,
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
                    BottomBarNavigator(
                        startScreen = bundle.startScreen
                    )
                }
            }
        }
    }

    private fun randomizeKey(key: String): String = createUniqueKey(key)

    private fun obtainDeepLinkNavigation() {
        val screenUrl = _deepLinkUri?.split("/")?.get(1)
        val targetScreen = _allowedDestinations.firstOrNull { it.key == screenUrl }

        if (targetScreen == null) {
            val deepScreens = _allowedDestinations.filter { it.screenType != ScreenType.Simple }
            deepScreens.forEach {
                if (deepLinkLaunch(destination = it, screenUrl = screenUrl)) return@forEach
            }
        } else {
            launch(
                screen = targetScreen.key,
                params = null
            )
        }
    }

    private fun deepLinkLaunch(destination: AllowedDestination, screenUrl: String?): Boolean {
        when (val screen = destination.screenType) {
            is ScreenType.Flow -> {
                val targetScreen = screen.flowBuilderModel.allowedDestination.firstOrNull { it.key == screenUrl }
                if (targetScreen != null) {
                    return true
                }
            }

            is ScreenType.MultiStack -> screen.multiStackBuilderModel
            else -> throw IllegalStateException("This ${destination.screenType} not supported")
        }

        return false
    }

    companion object {
        private const val flowKey = "odyssey_flow_reserved_type"
        private const val modalSheet = "odyssey_modal_sheet_reserved_type"
        private const val multiStackKey = "odyssey_multi_stack_reserved_type"
    }
}