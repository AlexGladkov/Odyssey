package ru.alexgladkov.odyssey.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import kotlinx.coroutines.flow.MutableStateFlow
import ru.alexgladkov.odyssey.compose.base.MultiStackNavigator
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.compose.helpers.*
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.BottomNavModel
import ru.alexgladkov.odyssey.compose.navigation.TabItem
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.NavConfiguration
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.animations.defaultPresentationAnimation
import ru.alexgladkov.odyssey.core.animations.defaultPushAnimation
import ru.alexgladkov.odyssey.core.backpress.BackPressedCallback
import ru.alexgladkov.odyssey.core.backpress.OnBackPressedDispatcher
import ru.alexgladkov.odyssey.core.extensions.CFlow
import ru.alexgladkov.odyssey.core.extensions.wrap
import ru.alexgladkov.odyssey.core.screen.Screen
import ru.alexgladkov.odyssey.core.wrap
import java.lang.IllegalStateException
import java.util.*
import kotlin.collections.HashMap

typealias Render<T> = @Composable (T) -> Unit

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
    private val _screenMap = mutableMapOf<String, Render<Any?>>()
    private val _keyList = mutableListOf<String>()
    private var _onBackPressedDispatcher: OnBackPressedDispatcher? = null

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
    fun updateScreenMap(screenMap: HashMap<String, Render<Any?>>) {
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
    fun present(key: String, params: Any? = null, launchFlag: LaunchFlag? = null) {
        launch(key, params, defaultPresentationAnimation(), launchFlag)
    }

    /**
     * Helper function to draw screen with push style (inner navigation)
     * @param screen - screen code, for example "splash". Must be included
     * in navigation graph or will cause an error
     * @param params - any bunch of params you need for the screen
     * @param launchFlag - flag if you want to change default behavior @see LaunchFlag
     */
    fun push(key: String, params: Any? = null, launchFlag: LaunchFlag? = null) {
        launch(key, params, defaultPushAnimation(), launchFlag)
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
        key: String, params: Any? = null,
        animationType: AnimationType = AnimationType.None,
        launchFlag: LaunchFlag? = null
    ) {
        val screenType = _allowedDestinations.find { it.key == key }?.screenType
            ?: throw IllegalStateException("Can't find screen in destination. Did you provide this screen?")

        when (screenType) {
            is ScreenType.Flow -> launchFlowScreen(key, params, animationType, screenType.flowBuilderModel, launchFlag)
            is ScreenType.BottomSheet -> TODO("Add bottom sheet implementation")
            is ScreenType.Simple -> launchSimpleScreen(key, params, animationType, launchFlag)
            is ScreenType.MultiStack -> launchMultiStackScreen(
                animationType,
                screenType.multiStackBuilderModel,
                screenType.bottomNavModel
            )
        }
    }

    /**
     * Returns to previous screen
     */
    fun popBackStack() {
        val last = _backstack.removeLast()
        if (_backstack.isEmpty()) {
            onApplicationFinish?.invoke()
            return
        }

        _keyList.remove(last.key)
        _currentScreen.value = _backstack.last()
            .copy(animationType = last.animationType, isForward = false)
            .wrap(with = last.key)
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

    private fun launchSimpleScreen(key: String, params: Any?, animationType: AnimationType, launchFlag: LaunchFlag?) {
        val strongKey = if (_keyList.contains(key))
            randomizeKey(key)
        else {
            _keyList.add(key)
            key
        }

        val screen = if (_backstack.isEmpty() && launchFlag == null) {
            Screen(key = strongKey, realKey = key, params = params)
        } else {
            Screen(key = strongKey, realKey = key, params = params, animationType = animationType)
        }

        when (launchFlag) {
            LaunchFlag.SingleNewTask -> _backstack.clear()
        }

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
            rootController.setupBackPressedDispatcher(null)
            rootController.parentRootController?.popBackStack()
        }
        rootController.setupBackPressedDispatcher(_onBackPressedDispatcher)
        rootController.updateScreenMap(flowBuilderModel.screenMap)
        rootController.setNavigationGraph(flowBuilderModel.allowedDestination)
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
        bottomNavModel: BottomNavModel
    ) {
        if (rootControllerType == RootControllerType.Flow || rootControllerType == RootControllerType.MultiStack)
            throw IllegalStateException("Don't use flow inside flow, call findRootController instead")

        val rootController = MultiStackRootController(bottomNavModel = bottomNavModel)
        rootController.setupTabItems(multiStackBuilderModel.tabItems)
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
                    Navigator(bundle.key, bundle.params)
                }
            }

            _screenMap[multiStackKey] = {
                val bundle = it as MultiStackBundle
                CompositionLocalProvider(
                    LocalRootController provides bundle.rootController
                ) {
                    MultiStackNavigator(null)
                }
            }
        }
    }

    private fun randomizeKey(key: String): String = "$key${UUID.randomUUID()}"

    companion object {
        private const val flowKey = "odyssey_flow_reserved_type"
        private const val multiStackKey = "odyssey_multi_stack_reserved_type"
    }
}