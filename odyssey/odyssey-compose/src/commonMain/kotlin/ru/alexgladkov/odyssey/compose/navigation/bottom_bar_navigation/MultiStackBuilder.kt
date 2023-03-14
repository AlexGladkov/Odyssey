package ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation

import ru.alexgladkov.odyssey.compose.AllowedDestination
import ru.alexgladkov.odyssey.compose.RenderWithParams
import ru.alexgladkov.odyssey.compose.helpers.FlowBuilderModel
import ru.alexgladkov.odyssey.compose.navigation.tabs.TabConfiguration

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
class TabInfo internal constructor(
    val tabConfiguration: TabConfiguration,
    val screenMap: HashMap<String, RenderWithParams<Any?>>,
    val allowedDestination: List<AllowedDestination>
)

/**
 * Builder for any multistack navigation with tabs
 * @param name - name for graph routing
 */
class MultiStackBuilder(val name: String) {
    private val _tabItems = mutableListOf<TabInfo>()

    fun addTab(tabConfiguration: TabConfiguration, flowBuilderModel: FlowBuilderModel) {
        _tabItems.add(TabInfo(tabConfiguration, flowBuilderModel.screenMap, flowBuilderModel.allowedDestination))
    }

    fun build(): MultiStackBuilderModel = MultiStackBuilderModel(
        name = name,
        tabItems = _tabItems
    )
}