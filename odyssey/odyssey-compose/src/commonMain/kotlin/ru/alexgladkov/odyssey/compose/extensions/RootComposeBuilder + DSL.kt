package ru.alexgladkov.odyssey.compose.extensions

import androidx.compose.runtime.Composable
import ru.alexgladkov.odyssey.compose.RenderWithParams
import ru.alexgladkov.odyssey.compose.helpers.*
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.*
import ru.alexgladkov.odyssey.compose.navigation.tabs.TabColors
import ru.alexgladkov.odyssey.compose.navigation.tabs.TabConfiguration
import ru.alexgladkov.odyssey.compose.navigation.tabs.TabContent
import ru.alexgladkov.odyssey.compose.navigation.tabs.TabDefaults
import ru.alexgladkov.odyssey.compose.navigation.top_navigation.TopBarColors
import ru.alexgladkov.odyssey.compose.navigation.top_navigation.TopBarDefaults

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
 * @param colors - colors configuration for bottom bar
 * @param elevation - elevation configuration for bottom bar
 * @param builder - dsl for bottom nav bar building
 */
@Composable
fun RootComposeBuilder.bottomNavigation(
    name: String,
    colors: BottomBarColors = BottomBarDefaults.bottomColors(),
    elevation: BottomBarElevations = BottomBarDefaults.elevation(),
    builder: @Composable MultiStackBuilder.() -> Unit
) {
    val configuration: MultiStackConfiguration = MultiStackConfiguration.BottomNavConfiguration(
        backgroundColor = colors.backgroundColor().value,
        defaultElevation = elevation.default().value
    )

    addMultiStack(
        key = name,
        displayConfiguration = configuration,
        multiStackBuilderModel = MultiStackBuilder(name).apply { builder.invoke(this) }.build()
    )
}

/**
 * Adds top bar navigation to navigation graph
 * @param name - name in graph
 * @param tabsNavModel - UI configuration for top navigation
 * @param builder - dsl for top nav bar building
 */
@Composable
fun RootComposeBuilder.topNavigation(
    name: String,
    colors: TopBarColors = TopBarDefaults.backgroundLineColors(),
    builder: @Composable MultiStackBuilder.() -> Unit
) {
    addMultiStack(
        key = name,
        displayConfiguration = MultiStackConfiguration.TopNavConfiguration(
            backgroundColor = colors.backgroundColor().value,
            dividerColor = colors.dividerColor().value,
            contentColor = colors.contentColor().value
        ),
        multiStackBuilderModel = MultiStackBuilder(name).apply { builder.invoke(this) }.build()
    )
}

/**
 * Adds custom bar navigation to navigation graph
 * @param name - name in graph
 * @param tabsNavModel - UI configuration for custom navigation
 * @param builder - dsl for custom nav bar building
 */
@Composable
fun RootComposeBuilder.customNavigation(
    name: String,
    content: @Composable (Any?) -> Unit,
    builder: @Composable MultiStackBuilder.() -> Unit
) {
    addMultiStack(
        key = name,
        displayConfiguration = MultiStackConfiguration.CustomNavConfiguration(content),
        multiStackBuilderModel = MultiStackBuilder(name).apply { builder.invoke(this) }.build()
    )
}

/**
 * Adds tab to bottom nav bar building
 * @param tabItem - tab UI configuration
 * @param builder - dsl buidler for flow in tab
 */
@Composable
fun MultiStackBuilder.tab(
    content: TabContent,
    colors: TabColors = TabDefaults.simpleTabColors(),
    builder: FlowBuilder.() -> Unit
) {
    val title = content.title().value
    val flow = FlowBuilder(title).apply(builder).build()
    addTab(
        TabConfiguration(
            title = title,
            icon = content.icon().value,
            selectedIconColor = colors.iconColor(true).value,
            unselectedIconColor = colors.iconColor(false).value,
            selectedTextColor = colors.textColor(true).value,
            unselectedTextColor = colors.textColor(false).value,
        ), flow
    )
}