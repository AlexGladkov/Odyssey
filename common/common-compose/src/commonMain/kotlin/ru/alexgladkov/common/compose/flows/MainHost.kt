package ru.alexgladkov.common.compose.flows

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.observeAsState
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.controllers.MultiStackRootController

@Composable
fun MainHost(rootController: RootController) {
    val multiStackRootController = rootController as MultiStackRootController
    val state = rootController.backStackObserver.observeAsState()

    val selectedColor = Color.DarkGray
    val unselectedColor = Color.LightGray

    state.value?.let { entry ->
        Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
            Box(modifier = Modifier.weight(1f)) {
                when (entry.rootController.debugName) {
                    NavigationTree.Tabs.Main.toString() -> MainFlow(entry.rootController)
                    NavigationTree.Tabs.Favorite.toString() -> FavoriteFlow(entry.rootController)
                    NavigationTree.Tabs.Settings.toString() -> SettingsFlow(entry.rootController)
                }
            }

            BottomNavigation(
                backgroundColor = Color.White
            ) {
                rootController.childrenRootController.forEach { flowRootController ->
                    val position = rootController.childrenRootController.indexOf(flowRootController)
                    val isSelected = state.value?.rootController == flowRootController
                    val bottomItemModel = multiStackRootController.childrenRootController[position]

                    BottomNavigationItem(
                        modifier = Modifier.background(Color.White),
                        selected = isSelected,
                        icon = {},
                        onClick = {
                            multiStackRootController.switchFlow(position, flowRootController)
                        },
                        label = {
                            Text(
                                text = bottomItemModel.debugName.toString(),
                                color = if (isSelected) selectedColor else unselectedColor
                            )
                        }
                    )
                }
            }
        }
    }
}