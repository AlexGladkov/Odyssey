package ru.alexgladkov.odyssey.compose.controllers

import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabInfo

data class TabNavigationModel(
    val tabInfo: TabInfo,
    val rootController: RootController
)