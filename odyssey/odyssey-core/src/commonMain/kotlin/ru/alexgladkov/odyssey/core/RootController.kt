package ru.alexgladkov.odyssey.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import kotlinx.coroutines.flow.MutableStateFlow
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
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.animations.defaultPresentationAnimation
import ru.alexgladkov.odyssey.core.animations.defaultPushAnimation
import ru.alexgladkov.odyssey.core.backpress.BackPressedCallback
import ru.alexgladkov.odyssey.core.backpress.OnBackPressedDispatcher
import ru.alexgladkov.odyssey.core.extensions.CFlow
import ru.alexgladkov.odyssey.core.extensions.wrap
import ru.alexgladkov.odyssey.core.screen.Screen
import kotlin.collections.HashMap

typealias RenderWithParams<T> = @Composable (T) -> Unit
typealias Render = @Composable () -> Unit

sealed class ScreenType {
    object Simple : ScreenType()
    data class Flow(val flowBuilderModel: FlowBuilderModel) : ScreenType()
    object BottomSheet : ScreenType()
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

    var parentRootController: RootController? = null
    var onApplicationFinish: (() -> Unit)? = null

    var currentScreen: CFlow<NavConfiguration> = _currentScreen.wrap()
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
     * Helper function to draw screen with presentation (aka modal) style
     * @param screen - screen code, for example "splash". Must be included
     * in navigation graph or will cause an error
     * @param params - any bunch of params you need for the screen
     * @param launchFlag - flag if you want to change default behavior @see LaunchFlag
     */
    fun present(screen: String, params: Any? = null, launchFlag: LaunchFlag? = null) {
        launch(screen, params, defaultPresentationAnimation(), launchFlag)
    }

    /**
     * Helper function to draw screen with push style (inner navigation)
     * @param screen - screen code, for example "splash". Must be included
     * in navigation graph or will cause an error
     * @param params - any bunch of params you need for the screen
     * @param launchFlag - flag if you want to change default behavior @see LaunchFlag
     */
    fun push(screen: String, params: Any? = null, launchFlag: LaunchFlag? = null) {
        launch(screen, params, defaultPushAnimation(), launchFlag)
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
        val screenType = _allowedDestinations.find { it.key == screen }?.screenType
            ?: throw IllegalStateException("Can't find screen in destination. Did you provide this screen?")

        when (screenType) {
            is ScreenType.Flow -> launchFlowScreen(
                screen,
                params,
                animationType,
                screenType.flowBuilderModel,
                launchFlag
            )
            is ScreenType.BottomSheet -> launchBottomSheetScreen(screen, launchFlag)
            is ScreenType.Simple -> launchSimpleScreen(screen, params, animationType, launchFlag)
            is ScreenType.MultiStack -> launchMultiStackScreen(
                animationType,
                screenType.multiStackBuilderModel,
                screenType.bottomNavModel,
                launchFlag
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

    fun drawCurrentScreen(startParams: Any? = null) {
        if (_backstack.isEmpty()) {
            launch(screen = _allowedDestinations.first().key, params = startParams)
        } else {
            val current = _backstack.last()
            _currentScreen.value = current.copy(animationType = AnimationType.None).wrap()
        }
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
                    .wrap(with = last.key)
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

    private fun launchBottomSheetScreen(key: String, launchFlag: LaunchFlag?) {
        when (launchFlag) {
            LaunchFlag.SingleNewTask -> _backstack.clear()
        }

        val screen = Screen(
            key = randomizeKey(key),
            realKey = key,
            animationType = defaultPresentationAnimation(),
            params = BottomSheetBundle(
                currentKey = _backstack.last().key,
                key = key
            )
        )

        _backstack.add(screen)
        _currentScreen.value = screen.wrap()
    }

    private fun launchFlowScreen(
        key: String,
        params: Any?, animationType: AnimationType,
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

        rootController.updateScreenMap(flowBuilderModel.screenMap)
        rootController.setNavigationGraph(flowBuilderModel.allowedDestination)
        _childrenRootController.add(rootController)

        val screen = Screen(
            key = flowKey,
            realKey = flowKey,
            animationType = animationType,
            params = FlowBundle(
                key = flowBuilderModel.allowedDestination.first().key,
                params = params, rootController = rootController
            )
        )

        _backstack.add(screen)
        _currentScreen.value = screen.wrap()
    }

    private fun launchMultiStackScreen(
        animationType: AnimationType,
        multiStackBuilderModel: MultiStackBuilderModel,
        bottomNavModel: BottomNavModel,
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
            rootController.updateScreenMap(it.screenMap)
            rootController.setNavigationGraph(it.allowedDestination)
            TabNavigationModel(tabInfo = it, rootController = rootController)
        }

        val rootController = MultiStackRootController(
            rootControllerType = RootControllerType.MultiStack,
            bottomNavModel = bottomNavModel,
            tabItems = configurations
        )

        _childrenRootController.add(rootController)

        val screen = Screen(
            key = multiStackKey,
            realKey = multiStackKey,
            animationType = animationType,
            params = MultiStackBundle(
                rootController = rootController
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
                    Navigator(bundle.params)
                }
            }

            _screenMap[multiStackKey] = {
                val bundle = it as MultiStackBundle
                CompositionLocalProvider(
                    LocalRootController provides bundle.rootController
                ) {
                    BottomBarNavigator()
                }
            }
        }
    }

    private fun randomizeKey(key: String): String = createUniqueKey(key)

    companion object {
        private const val flowKey = "odyssey_flow_reserved_type"
        private const val modalSheet = "odyssey_modal_sheet_reserved_type"
        private const val multiStackKey = "odyssey_multi_stack_reserved_type"
    }
}