package ru.alexgladkov.odyssey.compose.controllers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.MultiStackConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabInfo
import ru.alexgladkov.odyssey.core.breadcrumbs.Breadcrumb
import ru.alexgladkov.odyssey.core.configuration.RootControllerType

data class TabNavigationModel(
    val tabInfo: TabInfo,
    val rootController: RootController
)

class MultiStackRootController(
    rootControllerType: RootControllerType,
    val displayConfiguration: MultiStackConfiguration
) : RootController(rootControllerType) {
    private val _tabItems: MutableList<TabNavigationModel> = mutableListOf()
    private var _currentTabPosition: Int = -1
    private val _stackChangeObserver: MutableStateFlow<TabNavigationModel?> =
        MutableStateFlow(null)

    override var debugName: String? = "MultiStackRootController"
    val stackChangeObserver = _stackChangeObserver.asStateFlow()
    val tabItems = _tabItems

    fun setupWithTabs(tabItems: List<TabNavigationModel>, startPosition: Int = 0) {
        if (startPosition >= tabItems.size) throw IllegalStateException("Setup error: Position must be less than tab items count")
        _tabItems.clear()
        _tabItems.addAll(tabItems)
        _stackChangeObserver.value = tabItems[startPosition]
    }

    @Deprecated("Use switchTab with position instead")
    fun switchTab(tabNavigationModel: TabNavigationModel) {
        switchTab(_tabItems.indexOf(tabNavigationModel))
    }

    fun switchTab(position: Int) {
        if (position >= _tabItems.size) throw IllegalStateException("Position must be less than tab items count")
        val beforeTabPosition = if (_currentTabPosition >= 0) _currentTabPosition else 0

        _currentTabPosition = position
        _stackChangeObserver.value = _tabItems[position]

        onScreenNavigate?.let { callback ->
            val previousName = _tabItems[beforeTabPosition].tabInfo.tabConfiguration.title

            val breadcrumb = Breadcrumb(
                absolutePath = buildBackstackPath(),
                controllerName = debugName ?: "MultiStackRootController",
                currentScreen = previousName,
                targetScreen = getCurrentTabName()
            )

            callback.invoke(breadcrumb)
        }
    }

    fun getCurrentTabName(): String {
        val currentPosition = if (_currentTabPosition >= 0) _currentTabPosition else 0
        return _tabItems[currentPosition].tabInfo.tabConfiguration.title
    }

    override fun buildBackstackPath(): String {
        return "${super.buildBackstackPath()}/${getCurrentTabName()}"
    }

    override fun popBackStack() {
        val rootController = _stackChangeObserver.value?.rootController
        rootController?.popBackStack()
    }
}