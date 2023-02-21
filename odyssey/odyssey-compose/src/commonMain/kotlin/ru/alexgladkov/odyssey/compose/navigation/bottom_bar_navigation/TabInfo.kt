package ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation

import ru.alexgladkov.odyssey.compose.AllowedDestination
import ru.alexgladkov.odyssey.compose.RenderWithParams

/**
 * Worker class for tab info
 */
class TabInfo internal constructor(
    val tabItem: TabItem,
    val screenMap: HashMap<String, RenderWithParams<Any?>>,
    val allowedDestination: List<AllowedDestination>
)