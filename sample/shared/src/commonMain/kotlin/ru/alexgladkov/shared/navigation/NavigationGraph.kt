package ru.alexgladkov.shared.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.shared.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.*
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.BottomBarDefaults
import ru.alexgladkov.odyssey.compose.navigation.tabs.TabDefaults
import ru.alexgladkov.odyssey.compose.navigation.top_navigation.TopBarDefaults
import ru.alexgladkov.shared.screens.ActionsScreen
import ru.alexgladkov.shared.screens.PresentedActionsScreen
import ru.alexgladkov.shared.screens.TabPresentedActionsScreen
import ru.alexgladkov.shared.screens.TabScreen
import ru.alexgladkov.shared.theme.Odyssey

@Composable
internal fun RootComposeBuilder.navigationGraph() {
    screen(NavigationTree.Actions.name) {
        ActionsScreen(count = 0)
    }

    screen(NavigationTree.Push.name) {
        ActionsScreen(count = it as? Int)
    }

    flow(NavigationTree.Present.name) {
        screen(NavigationTree.PresentScreen.name) {
            PresentedActionsScreen(count = (it as? Int) ?: 0)
        }
    }

    flow(NavigationTree.TabPresent.name) {
        screen(NavigationTree.TabPresentScreen.name) {
            TabPresentedActionsScreen(count = (it as? Int) ?: 0)
        }

        screen(NavigationTree.Push.name) {
            TabPresentedActionsScreen(count = it as? Int)
        }
    }

    mainScreen()
    topNavScreen()
//    customNavScreen()
}

@Composable
internal fun RootComposeBuilder.mainScreen() {
    bottomNavigation(
        name = NavigationTree.Main.name,
        colors = BottomBarDefaults.bottomColors(
            backgroundColor = Odyssey.color.primaryBackground
        )
    ) {
        val colors = TabDefaults.simpleTabColors(
            selectedColor = Odyssey.color.primaryText,
            unselectedColor = Odyssey.color.controlColor
        )

        tab(TabDefaults.content(title = "Feed", vector = Icons.Default.List), colors) {
            screen(name = NavigationTree.Tab.name) {
                TabScreen(it as? Int)
            }
        }
        tab(TabDefaults.content(title = "Search", vector = Icons.Default.Search), colors) {
            screen(name = NavigationTree.Tab.name) {
                TabScreen(it as? Int)
            }
        }
        tab(TabDefaults.content(title = "Settings", vector = Icons.Default.Settings), colors) {
            screen(name = NavigationTree.Tab.name) {
                TabScreen(it as? Int)
            }
        }
    }
}

@Composable
fun RootComposeBuilder.topNavScreen() {
    topNavigation(
        name = NavigationTree.Top.name,
        colors = TopBarDefaults.backgroundLineColors(
            backgroundColor = Odyssey.color.primaryBackground,
            contentColor = Color.White
        )
    ) {
        val colors = TabDefaults.simpleTabColors(
            selectedColor = Odyssey.color.primaryText,
            unselectedColor = Odyssey.color.controlColor
        )

        tab(TabDefaults.content(title = "Feed", vector = null), colors) {
            screen(name = NavigationTree.Tab.name) {
                TabScreen(it as? Int)
            }
        }
        tab(TabDefaults.content(title = "Search", vector = null), colors) {
            screen(name = NavigationTree.Tab.name) {
                TabScreen(it as? Int)
            }
        }
        tab(TabDefaults.content(title = "Settings", vector = null), colors) {
            screen(name = NavigationTree.Tab.name) {
                TabScreen(it as? Int)
            }
        }
    }
}
//
//fun RootComposeBuilder.customNavScreen() {
//    customNavigation(name = NavigationTree.Drawer.name,
//        tabsNavModel = CustomConfiguration(
//            content = { params -> DrawerScreen(params) }
//        )) {
//        tab(FeedTab()) {
//            screen(name = NavigationTree.Tab.name) {
//                TabScreen(it as? Int)
//            }
//        }
//        tab(SearchTab()) {
//            screen(name = NavigationTree.Tab.name) {
//                TabScreen(it as? Int)
//            }
//        }
//        tab(CartTab()) {
//            screen(name = NavigationTree.Tab.name) {
//                TabScreen(it as? Int)
//            }
//        }
//    }
//}
