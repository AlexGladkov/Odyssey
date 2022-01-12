package ru.alexgladkov.odyssey.compose.navigation.bottom

import ru.alexgladkov.odyssey.compose.AllowedDestination
import ru.alexgladkov.odyssey.compose.Render
import ru.alexgladkov.odyssey.compose.helpers.FlowBuilder
import ru.alexgladkov.odyssey.compose.helpers.FlowBuilderModel

/**
 * Worker class for multistack
 */
data class MultiStackBuilderModel(
    val name: String,
    val tabItems: List<TabInfo>
)

/**
 * Worker class for tab info
 */
data class TabInfo(
    val tabItem: TabItem,
    val screenMap: HashMap<String, Render<Any?>>,
    val allowedDestination: List<AllowedDestination>
)

/**
 * Builder for any multistack navigation with tabs
 * @param name - name for graph routing
 */
class MultiStackBuilder(val name: String) {
    private val _tabItems = mutableListOf<TabInfo>()

    fun addTab(tabItem: TabItem, flowBuilderModel: FlowBuilderModel) {
        _tabItems.add(TabInfo(tabItem, flowBuilderModel.screenMap, flowBuilderModel.allowedDestination))
    }

    fun build(): MultiStackBuilderModel = MultiStackBuilderModel(
        name = name,
        tabItems = _tabItems
    )
}