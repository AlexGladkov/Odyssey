package ru.alexgladkov.odyssey.compose.controllers

import kotlinx.coroutines.flow.MutableStateFlow
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.navigation.BottomNavModel
import ru.alexgladkov.odyssey.compose.navigation.TabItem
import ru.alexgladkov.odyssey.core.extensions.CFlow
import ru.alexgladkov.odyssey.core.extensions.wrap

class MultiStackRootController(
    val bottomNavModel: BottomNavModel
) : RootController() {
    private val _tabList = mutableListOf<TabItem>()
    private val _stackChangeObserver: MutableStateFlow<TabItem?> = MutableStateFlow(null)
    val stackChangeObserver: CFlow<TabItem?> = _stackChangeObserver.wrap()
    val tabList: List<TabItem> = _tabList

    fun setupTabItems(tabItems: List<TabItem>) {
        _tabList.clear()
        _tabList.addAll(tabItems)
    }

    fun switchTab(tabItem: TabItem) {
        _stackChangeObserver.value = tabItem
    }
}