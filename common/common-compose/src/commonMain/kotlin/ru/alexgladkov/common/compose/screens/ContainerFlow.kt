package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.common.compose.`compose-extension`.ComposeRenderable
import ru.alexgladkov.common.compose.screens.favorite.FavoriteFlow
import ru.alexgladkov.common.compose.screens.main.MainFlow
import ru.alexgladkov.common.compose.screens.settings.SettingsFlow
import ru.alexgladkov.odyssey.compose.RootHost
import ru.alexgladkov.odyssey.compose.rememberRootController
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.NavigationScreen
import ru.alexgladkov.odyssey.core.RootController

@Composable
fun ContainerFlow(parentRootController: RootController) {
    val containerRootController = rememberRootController(parentRootController)
    containerRootController.debugName = "Container"

    val items = listOf(
        NavigationTree.Tabs.Main,
        NavigationTree.Tabs.Favorite,
        NavigationTree.Tabs.Settings,
    )

    val currentDestination: MutableState<NavigationScreen> = remember { mutableStateOf(NavigationTree.Tabs.Main) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                RootHost(startScreen = NavigationTree.Tabs.Main, containerRootController) {
                    flow(screen = NavigationTree.Tabs.Main) {
                        val flowRootController = this
                        ComposeRenderable(
                            render = { MainFlow(flowRootController) }
                        )
                    }

                    flow(screen = NavigationTree.Tabs.Favorite) {
                        val flowRootController = this
                        ComposeRenderable(
                            render = { FavoriteFlow(flowRootController) }
                        )
                    }

                    flow(screen = NavigationTree.Tabs.Settings) {
                        val flowRootController = this
                        ComposeRenderable(
                            render = { SettingsFlow(flowRootController) }
                        )
                    }
                }
            }

            Box(modifier = Modifier.height(56.dp)) {
                BottomNavigation(
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) {
                    items.forEach { bottomItemModel ->
                        val isSelected = currentDestination.value == bottomItemModel

                        BottomNavigationItem(
                            modifier = Modifier.background(Color.White),
                            icon = {},
                            label = {
                                Text(
                                    text = bottomItemModel.toString(),
                                    color = if (isSelected) Color.DarkGray else Color.LightGray
                                )
                            },
                            selected = isSelected,
                            onClick = {
                                currentDestination.value = bottomItemModel
                                containerRootController.launch(bottomItemModel) {
                                    launchFlag(LaunchFlag.SingleTop)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}