package ru.alexgladkov.odyssey.compose.base

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.alexgladkov.odyssey.compose.base.local.LocalBottomConfiguration
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.MultiStackConfiguration

@Composable
fun BottomBarNavigator(
    startScreen: String?,
    bottomNavConfiguration: MultiStackConfiguration.BottomNavConfiguration
) {
    val rootController = LocalRootController.current as MultiStackRootController
    val tabItem = rootController.stackChangeObserver.collectAsState().value ?: return

    Column(modifier = Modifier.fillMaxSize()) {
        TabNavigator(modifier = Modifier.weight(1f), startScreen, tabItem)

        BottomNavigation(
            backgroundColor = bottomNavConfiguration.backgroundColor,
            elevation = bottomNavConfiguration.defaultElevation
        ) {
            rootController.tabItems.forEach { currentItem ->
                val configuration = currentItem.tabInfo.tabConfiguration
                val position = rootController.tabItems.indexOf(currentItem)
                val isSelected = tabItem == currentItem

                BottomNavigationItem(
                    selected = isSelected,
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White,
                    icon = {
                        configuration.icon?.let {
                            Icon(
                                painter = it,
                                contentDescription = configuration.title,
                                tint = if (isSelected) configuration.selectedIconColor else configuration.unselectedIconColor
                            )
                        }
                    },
                    label = {
                        Text(
                            text = configuration.title,
                            color = if (isSelected) configuration.selectedTextColor else configuration.unselectedTextColor,
                            style = configuration.titleStyle ?: LocalTextStyle.current
                        )
                    },
                    onClick = {
                        rootController.switchTab(position)
                    })
            }
        }
    }

//    rootController.tabsNavModel.launchedEffect.invoke()
}

@Composable
fun TopBarNavigator(startScreen: String?, navConfiguration: MultiStackConfiguration.TopNavConfiguration) {
    val rootController = LocalRootController.current as MultiStackRootController
    val tabItem = rootController.stackChangeObserver.collectAsState().value ?: return

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            backgroundColor = navConfiguration.backgroundColor,
            contentColor = navConfiguration.contentColor,
            selectedTabIndex = rootController.tabItems.indexOfFirst { it == tabItem }
                .coerceAtLeast(0),
            divider = {
                TabRowDefaults.Divider(
                    color = navConfiguration.dividerColor
                )
            }
        ) {
            rootController.tabItems.forEach { currentItem ->
                val configuration = currentItem.tabInfo.tabConfiguration
                val position = rootController.tabItems.indexOf(currentItem)
                val isSelected = tabItem == currentItem

                Tab(
                    selected = isSelected,
                    onClick = { rootController.switchTab(position) },
                    text = {
                        Text(
                            text = configuration.title,
                            color = if (isSelected) configuration.selectedTextColor else configuration.unselectedTextColor,
                            style = configuration.titleStyle ?: LocalTextStyle.current
                        )
                    }
                )
            }
        }

        TabNavigator(modifier = Modifier.weight(1f), startScreen, tabItem)
    }

//    rootController.tabsNavModel.launchedEffect.invoke()
}