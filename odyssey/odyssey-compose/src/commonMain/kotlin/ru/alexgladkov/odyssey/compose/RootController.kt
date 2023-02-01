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
import ru.alexgladkov.odyssey.core.CoreRootController
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.NavConfiguration
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.backpress.BackPressedCallback
import ru.alexgladkov.odyssey.core.backpress.OnBackPressedDispatcher
import ru.alexgladkov.odyssey.core.breadcrumbs.Breadcrumb
import ru.alexgladkov.odyssey.core.configuration.DisplayType
import ru.alexgladkov.odyssey.core.configuration.RootConfiguration
import ru.alexgladkov.odyssey.core.configuration.RootControllerType
import ru.alexgladkov.odyssey.core.screen.Screen
import ru.alexgladkov.odyssey.core.screen.ScreenBundle
import ru.alexgladkov.odyssey.core.wrap
import kotlin.collections.HashMap

typealias RenderWithParams<T> = @Composable (T) -> Unit
typealias Render = @Composable (key: String) -> Unit

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

open class RootController(rootControllerType: RootControllerType): CoreRootController<Screen>(rootControllerType) {
    private val _allowedDestinations: MutableList<AllowedDestination> = mutableListOf()
    override val _backstack = mutableListOf<Screen>()
    private val _currentScreen: MutableStateFlow<NavConfiguration?> =
        MutableStateFlow(null)
    private var _childrenRootController: MutableList<RootController> = mutableListOf()
    private val _screenMap = LinkedHashMap<String, RenderWithParams<Any?>>()
    private var _onBackPressedDispatcher: OnBackPressedDispatcher? = null
    private var _modalController: ModalController? = null
    private var _deepLinkUri: String? = null

    override var onScreenNavigate: ((Breadcrumb) -> Unit)? = null
    var parentRootController: RootController? = null
    var backgroundColor: Color = Color.White
    var onApplicationFinish: (() -> Unit)? = null
    var onScreenRemove: (ScreenBundle) -> Unit =
        { parentRootController?.onScreenRemove?.invoke(it) }

    var currentScreen: StateFlow<NavConfiguration?> = _currentScreen.asStateFlow()

    /**
     * Debug name need to debug :) if you like console debugging
     * Setup automatically
     */
    open var debugName: String? = if (parentRootController == null) "Root" else null
        protected set

    init {
        initServiceScreens()
    }

    // Get screen render compose function
    fun getScreenRender(screenName: String?): RenderWithParams<Any?>? {
        return when {
            screenName?.contains(multiStackKey) == true -> _screenMap[multiStackKey]
            screenName?.contains(flowKey) == true -> _screenMap[flowKey]
            else -> _screenMap[screenName]
        }
    }

    // Render screen with params
    @Deprecated(
        "Use renderScreen function instead",
        ReplaceWith("renderScreen(screenName, params)")
    )
    @Composable
    fun RenderScreen(screenName: String?, params: Any?) {
        renderScreen(screenName, params)
    }

    @Composable
    fun renderScreen(screenName: String?, params: Any?) {
        _screenMap[screenName]?.invoke(params)
    }

    /**
     * Returns first key from navigation graph
     */
    fun getFirstScreenName(): String? = _screenMap.keys.firstOrNull()

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

        handleScreenBreadcrumbs(targetKey = screen)

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
                screenName = screen,
                animationType = animationType,
                multiStackBuilderModel = screenType.multiStackBuilderModel,
                tabsNavModel = screenType.tabsNavModel,
                launchFlag = launchFlag,
                startScreen = startScreen,
                startTabPosition = startTabPosition,
                params = params
            )
        }
    }

    // Returns to previous screen
    open fun popBackStack() {
        if (_modalController?.isEmpty() == false) {
            _modalController?.popBackStack()
            return
        }

        val realKey = _backstack.last().realKey
        when {
            realKey.contains(flowKey) -> removeTopScreen(_childrenRootController.last())
            realKey.contains(multiStackKey) -> _childrenRootController.last().popBackStack()
            else -> removeTopScreen(this)
        }
    }

    /**
     * back to  the first screen by name recursively
     */
    fun backToScreen(screenName: String) {
        _modalController?.clearBackStack()

        val realKey = _backstack.last().realKey
        when {
            realKey.contains(flowKey) -> backToScreen(_childrenRootController.last(), screenName)
            realKey.contains(multiStackKey) -> backToScreen(
                _childrenRootController.last(),
                screenName
            )

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

    // Returns your MultiStack host if it presented
    fun findHostController(): MultiStackRootController? {
        if (rootControllerType == RootControllerType.MultiStack) return this as? MultiStackRootController
        if (rootControllerType == RootControllerType.Tab) {
            return parentRootController as? MultiStackRootController
        }

        return null
    }

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
        } ?: throw IllegalStateException("Can't launch $_deepLinkUri to unknown screen")

        launch(
            screen = destination.key,
            startScreen = searchKey,
            startTabPosition = startTabPosition,
            params = params,
            animationType = animationType,
            launchFlag = launchFlag,
            deepLink = false
        )

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
                    val clearedKey = cleanRealKeyFromType(current.realKey)
                    if (clearedKey == screenName) {
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
                val clearedKey = cleanRealKeyFromType(current.realKey)

                if (clearedKey == screenName) {
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
        rootController?.let {
            if (it._backstack.size <= 1) {
                if (it.debugName == "Root") {
                    it.onApplicationFinish?.invoke()
                } else {
                    removeTopScreen(it.parentRootController)
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
        val screen = Screen(
            key = randomizeKey(key),
            realKey = key,
            params = params,
            animationType = if (_backstack.isEmpty() && launchFlag == null) AnimationType.None else animationType
        )

        handleLaunchFlag(key, launchFlag)

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
        if (rootControllerType == RootControllerType.Flow) throw IllegalStateException(
            "Don't use flow inside flow, call findRootController() instead"
        )

        val compositeKey = "$flowKey$$key"
        handleLaunchFlag(compositeKey, launchFlag)

        val rootController = RootController(RootControllerType.Flow)
        rootController.backgroundColor = backgroundColor

        rootController.debugName = key
        rootController.parentRootController = this
        rootController.onScreenNavigate = onScreenNavigate
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
            key = randomizeKey(compositeKey),
            realKey = compositeKey,
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
        screenName: String,
        animationType: AnimationType,
        multiStackBuilderModel: MultiStackBuilderModel,
        tabsNavModel: TabsNavModel<*>,
        startScreen: String? = null,
        startTabPosition: Int = 0,
        launchFlag: LaunchFlag?,
        params: Any? = null,
    ) {
        if (rootControllerType == RootControllerType.Flow || rootControllerType == RootControllerType.MultiStack)
            throw IllegalStateException("Don't use flow inside flow, call findRootController instead")

        val multiStackRealKey = "$multiStackKey$$screenName"
        handleLaunchFlag(multiStackRealKey, launchFlag)

        val parentRootController = this
        val multiStackRootController = MultiStackRootController(
            rootControllerType = RootControllerType.MultiStack,
            tabsNavModel = tabsNavModel,
        )

        multiStackRootController.setDeepLinkUri(_deepLinkUri)
        multiStackRootController.parentRootController = parentRootController
        multiStackRootController.onScreenNavigate = onScreenNavigate

        val configurations = multiStackBuilderModel.tabItems.map {
            val rootController =
                RootController(RootControllerType.Tab)
            rootController.backgroundColor = backgroundColor
            rootController.parentRootController = multiStackRootController
            rootController.debugName = it.tabItem.name
            rootController.onScreenNavigate = onScreenNavigate
            rootController.setDeepLinkUri(_deepLinkUri)
            rootController.updateScreenMap(it.screenMap)
            rootController.setNavigationGraph(it.allowedDestination)
            TabNavigationModel(tabInfo = it, rootController = rootController)
        }

        multiStackRootController.setupWithTabs(configurations, startTabPosition)
        _childrenRootController.add(multiStackRootController)

        val screen = Screen(
            key = randomizeKey(multiStackRealKey),
            realKey = multiStackRealKey,
            animationType = animationType,
            params = MultiStackBundle(
                rootController = multiStackRootController,
                startScreen = startScreen,
                params = params
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
                            customNavConfiguration.content(bundle.params)
                        }
                    }
                }
            }
        }
    }

    private fun handleScreenBreadcrumbs(targetKey: String) {
        if (onScreenNavigate == null) return
        val lastScreen = _backstack.lastOrNull() ?: return
        val cleanedRealKey = cleanRealKeyFromType(lastScreen.realKey)

        val breadcrumb = Breadcrumb(
            absolutePath = "${buildBackstackPath()}.$targetKey",
            currentScreen = cleanedRealKey,
            targetScreen = targetKey,
            controllerName = debugName ?: "Root"
        )

        onScreenNavigate?.invoke(breadcrumb)
    }

    protected open fun buildBackstackPath(): String {
        // TODO: Optimize this path
        if (rootControllerType == RootControllerType.Tab) {
            val parent = parentRootController as? MultiStackRootController ?: return ""
            val name = parent.getCurrentTabName()
            val stringBuilder = StringBuilder()
            stringBuilder.append("${parentRootController?.buildBackstackPath()}/$name")

            _backstack.forEach {
                stringBuilder.append(".${cleanRealKeyFromType(it.realKey)}")
            }

            return stringBuilder.toString()
        } else {
            var parent = parentRootController
            val stringBuilder = StringBuilder()
            while (parent != null) {
                stringBuilder.append(parentRootController!!.buildBackstackPath())
                parent = parent.parentRootController
            }

            stringBuilder.append("/$debugName")
            _backstack.forEach {
                stringBuilder.append(".${cleanRealKeyFromType(it.realKey)}")
            }

            return stringBuilder.toString()
        }
    }

    private fun handleLaunchFlag(screenKey: String, launchFlag: LaunchFlag?) {
        when (launchFlag) {
            LaunchFlag.SingleNewTask -> {
                _modalController?.clearBackStack()
                _backstack.clear()
            }
            LaunchFlag.SingleInstance -> {
                _modalController?.clearBackStack()
                _backstack.removeAll { it.realKey == screenKey }
            }
            LaunchFlag.ClearPrevious -> {
                _modalController?.clearBackStack()
                _backstack.removeLastOrNull()
            }
            null -> {}
        }
    }

    companion object {
        internal fun randomizeKey(key: String): String = createUniqueKey(key)
    }
}