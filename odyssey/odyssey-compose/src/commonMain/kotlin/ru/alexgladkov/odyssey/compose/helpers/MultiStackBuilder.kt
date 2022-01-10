package ru.alexgladkov.odyssey.compose.helpers

import ru.alexgladkov.odyssey.compose.AllowedDestination
import ru.alexgladkov.odyssey.compose.Render
import ru.alexgladkov.odyssey.compose.navigation.TabItem

data class MultiStackBuilderModel(
    val name: String,
    val tabItems: List<TabInfo>
)

data class TabInfo(
    val tabItem: TabItem,
    val screenMap: HashMap<String, Render<Any?>>,
    val allowedDestination: List<AllowedDestination>
)

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

fun MultiStackBuilder.tab(tabItem: TabItem, builder: FlowBuilder.() -> Unit) {
    val flow = FlowBuilder(tabItem.name).apply(builder).build()
    addTab(tabItem, flow)
}