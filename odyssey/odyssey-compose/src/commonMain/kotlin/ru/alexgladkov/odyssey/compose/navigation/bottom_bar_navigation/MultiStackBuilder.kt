package ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation

import ru.alexgladkov.odyssey.compose.AllowedDestination
import ru.alexgladkov.odyssey.compose.RenderWithParams
import ru.alexgladkov.odyssey.compose.helpers.FlowBuilderModel

/**
 * Worker class for multistack
 */
data class MultiStackBuilderModel(
    val name: String,
    val tabItems: List<TabInfo>
)

/**
 * Builder for any multistack navigation with tabs
 * @param name - name for graph routing
 */
class MultiStackBuilder(val name: String) {
    private val _tabItems = mutableListOf<TabInfo>()

    internal fun addTab(tabItem: TabItem, flowBuilderModel: FlowBuilderModel) {
        _tabItems.add(TabInfo(tabItem, flowBuilderModel.screenMap, flowBuilderModel.allowedDestination))
    }

    internal fun build(): MultiStackBuilderModel = MultiStackBuilderModel(
        name = name,
        tabItems = _tabItems
    )
}