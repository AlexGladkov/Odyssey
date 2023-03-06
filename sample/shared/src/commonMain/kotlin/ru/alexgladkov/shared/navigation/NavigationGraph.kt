package ru.alexgladkov.shared.navigation

import ru.alexgladkov.shared.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.*
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.shared.screens.ActionsScreen
import ru.alexgladkov.shared.screens.DrawerScreen
import ru.alexgladkov.shared.screens.PresentedActionsScreen
import ru.alexgladkov.shared.screens.TabPresentedActionsScreen
import ru.alexgladkov.shared.screens.TabScreen
import ru.alexgladkov.shared.tabs.BottomConfiguration
import ru.alexgladkov.shared.tabs.CartTab
import ru.alexgladkov.shared.tabs.CustomConfiguration
import ru.alexgladkov.shared.tabs.FeedTab
import ru.alexgladkov.shared.tabs.SearchTab
import ru.alexgladkov.shared.tabs.TopConfiguration

fun RootComposeBuilder.navigationGraph() {
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
    customNavScreen()
}

fun RootComposeBuilder.mainScreen() {
    bottomNavigation(name = NavigationTree.Main.name, tabsNavModel = BottomConfiguration()) {
        tab(FeedTab()) {
            screen(name = NavigationTree.Tab.name) {
                TabScreen(it as? Int)
            }
        }
        tab(SearchTab()) {
            screen(name = NavigationTree.Tab.name) {
                TabScreen(it as? Int)
            }
        }
        tab(CartTab()) {
            screen(name = NavigationTree.Tab.name) {
                TabScreen(it as? Int)
            }
        }
    }
}

fun RootComposeBuilder.topNavScreen() {
    topNavigation(name = NavigationTree.Top.name, tabsNavModel = TopConfiguration()) {
        tab(FeedTab()) {
            screen(name = NavigationTree.Tab.name) {
                TabScreen(it as? Int)
            }
        }
        tab(SearchTab()) {
            screen(name = NavigationTree.Tab.name) {
                TabScreen(it as? Int)
            }
        }
        tab(CartTab()) {
            screen(name = NavigationTree.Tab.name) {
                TabScreen(it as? Int)
            }
        }
    }
}

fun RootComposeBuilder.customNavScreen() {
    customNavigation(name = NavigationTree.Drawer.name,
        tabsNavModel = CustomConfiguration(
            content = { params -> DrawerScreen(params) }
        )) {
        tab(FeedTab()) {
            screen(name = NavigationTree.Tab.name) {
                TabScreen(it as? Int)
            }
        }
        tab(SearchTab()) {
            screen(name = NavigationTree.Tab.name) {
                TabScreen(it as? Int)
            }
        }
        tab(CartTab()) {
            screen(name = NavigationTree.Tab.name) {
                TabScreen(it as? Int)
            }
        }
    }
}
