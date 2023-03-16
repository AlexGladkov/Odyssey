package ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import ru.alexgladkov.odyssey.compose.AllowedDestination
import ru.alexgladkov.odyssey.compose.RenderWithParams
import ru.alexgladkov.odyssey.compose.helpers.FlowBuilderModel
import ru.alexgladkov.odyssey.compose.navigation.tabs.TabConfiguration

/**
 * Worker class for multistack
 */
@Stable
data class MultiStackBuilderModel(
    val name: String,
    val tabItems: ImmutableList<TabInfo>
)

/**
 * Worker class for tab info
 */
@Stable
class TabInfo internal constructor(
    val tabConfiguration: TabConfiguration,
    val screenMap: ImmutableMap<String, RenderWithParams<Any?>>,
    val allowedDestination: ImmutableList<AllowedDestination>
)

/**
 * Builder for any multistack navigation with tabs
 * @param name - name for graph routing
 */
@Stable
class MultiStackBuilder(val name: String) {
    private val _tabItems = mutableListOf<TabInfo>()

    fun addTab(tabConfiguration: TabConfiguration, flowBuilderModel: FlowBuilderModel) {
        _tabItems.add(TabInfo(tabConfiguration, flowBuilderModel.screenMap, flowBuilderModel.allowedDestination))
    }

    fun build(): MultiStackBuilderModel = MultiStackBuilderModel(
        name = name,
        tabItems = _tabItems.toImmutableList()
    )
}