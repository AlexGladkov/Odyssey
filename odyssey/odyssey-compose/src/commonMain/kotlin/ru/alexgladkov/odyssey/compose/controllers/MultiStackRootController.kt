package ru.alexgladkov.odyssey.compose.controllers

import androidx.compose.ui.graphics.Color
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
    val tabsNavModel: TabsNavModel<*>,
    val tabItems: List<TabNavigationModel>,
    startTabPosition: Int
) : RootController(rootControllerType) {
    private val _stackChangeObserver: MutableStateFlow<TabNavigationModel> =
        MutableStateFlow(tabItems[startTabPosition])
    val stackChangeObserver = _stackChangeObserver.asStateFlow()

    fun switchTab(tabConfiguration: TabNavigationModel) {
        _stackChangeObserver.value = tabConfiguration
    }

    override fun popBackStack() {
        val rootController = _stackChangeObserver.value.rootController
        rootController.popBackStack()
    }
}