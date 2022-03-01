package ru.alexgladkov.odyssey.core.controllers

import kotlinx.coroutines.flow.MutableStateFlow
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.RootControllerType
import ru.alexgladkov.odyssey.core.extensions.CFlow
import ru.alexgladkov.odyssey.core.extensions.wrap
import ru.alexgladkov.odyssey.core.navigation.bottom_bar_navigation.BottomNavModel
import ru.alexgladkov.odyssey.core.navigation.bottom_bar_navigation.TabInfo

data class TabNavigationModel(
    val tabInfo: TabInfo,
    val rootController: RootController
)

class MultiStackRootController(
    val rootControllerType: RootControllerType,
    val bottomNavModel: BottomNavModel,
    val tabItems: List<TabNavigationModel>
) : RootController(rootControllerType) {
    private val _stackChangeObserver: MutableStateFlow<TabNavigationModel> = MutableStateFlow(tabItems.first())
    val stackChangeObserver: CFlow<TabNavigationModel> = _stackChangeObserver.wrap()

    fun switchTab(tabConfiguration: TabNavigationModel) {
        _stackChangeObserver.value = tabConfiguration
    }

    override fun popBackStack() {
        val rootController = _stackChangeObserver.value.rootController
        rootController.popBackStack()
    }
}