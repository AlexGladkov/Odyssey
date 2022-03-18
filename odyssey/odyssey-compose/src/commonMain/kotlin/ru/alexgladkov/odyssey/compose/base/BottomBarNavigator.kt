package ru.alexgladkov.odyssey.compose.base

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.compose.controllers.TabNavigationModel
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.toScreenBundle

@Composable
fun ColumnScope.TabNavigator(currentTab: TabNavigationModel) {
    val configuration = currentTab.rootController.currentScreen.collectAsState()
    val saveableStateHolder = rememberSaveableStateHolder()

    saveableStateHolder.SaveableStateProvider(currentTab.tabInfo.tabItem.name) {
        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
            CompositionLocalProvider(
                LocalRootController provides currentTab.rootController
            ) {
                configuration.value.let { navConfig ->
                    AnimatedHost(
                        currentScreen = navConfig.screen.toScreenBundle(),
                        animationType = navConfig.screen.animationType,
                        screenToRemove = navConfig.screenToRemove?.toScreenBundle(),
                        isForward = navConfig.screen.isForward,
                        onScreenRemove = currentTab.rootController.onScreenRemove
                    ) {
                        val rootController = currentTab.rootController
                        rootController.RenderScreen(it.realKey, it.params)
                    }
                }
            }
        }
    }

    LaunchedEffect(currentTab) {
        currentTab.rootController.drawCurrentScreen()
    }
}

@Composable
fun BottomBarNavigator() {
    val rootController = LocalRootController.current as MultiStackRootController
    val tabItem = rootController.stackChangeObserver.collectAsState()
    val bottomNavConfiguration = rootController.bottomNavModel.bottomNavConfiguration

    Column(modifier = Modifier.fillMaxSize()) {
        TabNavigator(tabItem.value)

        BottomNavigation(
            backgroundColor = bottomNavConfiguration.backgroundColor
        ) {
            rootController.tabItems.forEach { currentItem ->
                val configuration = currentItem.tabInfo.tabItem.configuration
                val isSelected = tabItem.value == currentItem

                BottomNavigationItem(
                    selected = isSelected,
                    selectedContentColor = configuration.selectedColor
                        ?: bottomNavConfiguration.selectedColor,
                    unselectedContentColor = configuration.unselectedColor
                        ?: bottomNavConfiguration.unselectedColor,
                    icon = {
                        if (isSelected) {
                            configuration.selectedIcon?.let {
                                Icon(
                                    painter = it,
                                    contentDescription = configuration.title
                                )
                            }
                        } else {
                            configuration.unselectedIcon?.let {
                                Icon(
                                    painter = it,
                                    contentDescription = configuration.title
                                )
                            }
                        }

                    },
                    label = {
                        Text(
                            text = configuration.title,
                            style = configuration.titleStyle ?: LocalTextStyle.current
                        )
                    },
                    onClick = {
                        rootController.switchTab(currentItem)
                    })
            }
        }
    }

    rootController.bottomNavModel.launchedEffect.invoke()
}