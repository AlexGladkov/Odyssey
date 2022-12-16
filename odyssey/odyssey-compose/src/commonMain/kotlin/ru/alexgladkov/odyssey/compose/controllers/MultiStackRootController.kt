package ru.alexgladkov.odyssey.compose.controllers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.RootControllerType
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabInfo
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabsNavModel

data class TabNavigationModel(
    val tabInfo: TabInfo,
    val rootController: RootController
)

class MultiStackRootController(
    val rootControllerType: RootControllerType,
    val tabsNavModel: TabsNavModel<*>
) : RootController(rootControllerType) {
    private val _tabItems: MutableList<TabNavigationModel> = mutableListOf()
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
        _stackChangeObserver.value = tabNavigationModel
    }

    fun switchTab(position: Int) {
        if (position >= _tabItems.size) throw IllegalStateException("Position must be less than tab items count")
        _stackChangeObserver.value = _tabItems[position]
    }

    override fun popBackStack() {
        val rootController = _stackChangeObserver.value?.rootController
        rootController?.popBackStack()
    }
}