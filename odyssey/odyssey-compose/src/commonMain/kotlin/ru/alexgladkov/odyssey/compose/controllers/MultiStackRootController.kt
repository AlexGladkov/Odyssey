package ru.alexgladkov.odyssey.compose.controllers

import kotlinx.coroutines.flow.MutableStateFlow
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.navigation.bottom.TabInfo
import ru.alexgladkov.odyssey.compose.navigation.bottom.BottomNavModel
import ru.alexgladkov.odyssey.core.extensions.CFlow
import ru.alexgladkov.odyssey.core.extensions.wrap

data class TabNavigationModel(
    val tabInfo: TabInfo,
    val rootController: RootController
)

class MultiStackRootController(
    val bottomNavModel: BottomNavModel,
    val tabItems: List<TabNavigationModel>
) : RootController() {
    private val _stackChangeObserver: MutableStateFlow<TabNavigationModel> = MutableStateFlow(tabItems.first())
    val stackChangeObserver: CFlow<TabNavigationModel> = _stackChangeObserver.wrap()

    fun switchTab(tabConfiguration: TabNavigationModel) {
        _stackChangeObserver.value = tabConfiguration
    }
}