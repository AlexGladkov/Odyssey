package ru.alexgladkov.odyssey.compose.base

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.compose.controllers.TabNavigationModel
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.NavConfiguration
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.extensions.Closeable
import ru.alexgladkov.odyssey.core.screen.ScreenBundle

@Composable
fun ColumnScope.TabNavigator(currentTab: TabNavigationModel) {
    var configuration: NavConfiguration? by remember { mutableStateOf(null) }
    val saveableStateHolder = rememberSaveableStateHolder()

    saveableStateHolder.SaveableStateProvider(currentTab.tabInfo.tabItem.name) {
        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
            CompositionLocalProvider(
                LocalRootController provides currentTab.rootController
            ) {
                configuration?.let { navConfig ->
                    val screenBundle = ScreenBundle(
                        key = navConfig.screen.key,
                        realKey = navConfig.screen.realKey,
                        params = navConfig.screen.params
                    )

                    AnimatedHost(
                        currentScreen = screenBundle,
                        animationType = navConfig.screen.animationType,
                        removeScreen = navConfig.removeScreen,
                        isForward = navConfig.screen.isForward
                    ) {
                        val rootController = currentTab.rootController
                        rootController.screenMap[it.realKey]?.invoke(it.params)
                    }
                }
            }
        }
    }

    LaunchedEffect(currentTab) {
        currentTab.rootController.drawCurrentScreen()
        currentTab.rootController.currentScreen.watch {
            configuration = it
        }
    }
}

@Composable
fun MultiStackNavigator(startParams: Any?) {
    val rootController = LocalRootController.current as MultiStackRootController
    var tabItem: TabNavigationModel by remember { mutableStateOf(rootController.tabItems.first()) }
    var stackCloseable: Closeable? = null

    Column(modifier = Modifier.fillMaxSize()) {
        TabNavigator(tabItem)

        val bottomNavConfiguration = rootController.bottomNavModel.bottomNavConfiguration
        BottomNavigation(
            backgroundColor = bottomNavConfiguration.backgroundColor
        ) {
            rootController.tabItems.forEach { currentItem ->
                val configuration = currentItem.tabInfo.tabItem.configuration

                BottomNavigationItem(
                    selected = tabItem == currentItem,
                    selectedContentColor = configuration.selectedColor
                        ?: bottomNavConfiguration.selectedColor,
                    unselectedContentColor = configuration.unselectedColor
                        ?: bottomNavConfiguration.unselectedColor,
                    icon = {
                        configuration.icon?.let {
                            Icon(
                                it,
                                contentDescription = configuration.title
                            )
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

    LaunchedEffect(Unit) {
        stackCloseable = rootController.stackChangeObserver.watch {
            tabItem = it
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            stackCloseable?.close()
        }
    }
}