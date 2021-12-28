package ru.alexgladkov.odyssey.compose.helpers

import ru.alexgladkov.odyssey.compose.Render
import ru.alexgladkov.odyssey.compose.navigation.TabItem

data class MultiStackBuilderModel(
    val name: String,
    val tabItems: List<TabItem>
)

class MultiStackBuilder(val name: String) {
    private val _screenMap: HashMap<String, Render<Any?>> = hashMapOf()
    private val _tabItems = mutableListOf<TabItem>()

    fun addTab(tabItem: TabItem) {
        _tabItems.add(tabItem)
    }

    fun build(): MultiStackBuilderModel = MultiStackBuilderModel(
        name = name,
        tabItems = _tabItems
    )
}

fun MultiStackBuilder.tab(tabItem: TabItem) {
    addTab(tabItem)
}