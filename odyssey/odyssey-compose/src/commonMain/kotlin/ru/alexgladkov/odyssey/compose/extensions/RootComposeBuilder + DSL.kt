package ru.alexgladkov.odyssey.compose.extensions

import ru.alexgladkov.odyssey.compose.RenderWithParams
import ru.alexgladkov.odyssey.compose.helpers.*
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.*

/**
 * Adds screen to navigation graph
 * @param name - name in graph
 * @param content - composable content
 */
fun RootComposeBuilder.screen(
    name: String,
    content: RenderWithParams<Any?>
) {
    addScreen(
        key = name,
        screenMap = hashMapOf(name to content)
    )
}

/**
 * Adds flow of screens to navigation graph
 * @param name - name in graph
 * @param builder - dsl for flow building
 */
fun RootComposeBuilder.flow(
    name: String,
    builder: FlowBuilder.() -> Unit
) {
    addFlow(
        key = name,
        flowBuilderModel = FlowBuilder(name).apply(builder).build()
    )
}

/**
 * Adds screen to flow builder
 * @param name - name in navigation graph
 * @param content - composable content
 */
fun FlowBuilder.screen(name: String, content: RenderWithParams<Any?>) {
    addScreen(name = name, content = content)
}

/**
 * Adds bottom bar navigation to navigation graph
 * @param name - name in graph
 * @param tabsNavModel - UI configuration for bottom navigation
 * @param builder - dsl for bottom nav bar building
 */
fun RootComposeBuilder.bottomNavigation(
    name: String,
    tabsNavModel: TabsNavModel<BottomNavConfiguration>,
    builder: MultiStackBuilder.() -> Unit
) {
    addMultiStack(
        key = name,
        tabsNavModel = tabsNavModel,
        multiStackBuilderModel = MultiStackBuilder(name).apply(builder).build()
    )
}


/**
 * Adds top bar navigation to navigation graph
 * @param name - name in graph
 * @param tabsNavModel - UI configuration for top navigation
 * @param builder - dsl for top nav bar building
 */
fun RootComposeBuilder.topNavigation(
    name: String,
    tabsNavModel: TabsNavModel<TopNavConfiguration>,
    builder: MultiStackBuilder.() -> Unit
) {
    addMultiStack(
        key = name,
        tabsNavModel = tabsNavModel,
        multiStackBuilderModel = MultiStackBuilder(name).apply(builder).build()
    )
}

/**
 * Adds tab to bottom nav bar building
 * @param tabItem - tab UI configuration
 * @param builder - dsl buidler for flow in tab
 */
fun MultiStackBuilder.tab(tabItem: TabItem, builder: FlowBuilder.() -> Unit) {
    val flow = FlowBuilder(tabItem.name).apply(builder).build()
    addTab(tabItem, flow)
}