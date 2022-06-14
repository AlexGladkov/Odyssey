package ru.alexgladkov.common.compose.navigation

import ru.alexgladkov.common.compose.screens.*
import ru.alexgladkov.odyssey.compose.extensions.*
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import screens.TabScreen
import tabs.*

fun RootComposeBuilder.topNavScreen() {
    topNavigation(name = "top", tabsNavModel = TopConfiguration()) {
        tab(FeedTab()) {
            screen(name = "tab") {
                TabScreen(it as? Int)
            }
        }
        tab(SearchTab()) {
            screen(name = "tab") {
                TabScreen(it as? Int)
            }
        }
        tab(CartTab()) {
            screen(name = "tab") {
                TabScreen(it as? Int)
            }
        }
    }
}

fun RootComposeBuilder.customNavScreen() {
    customNavigation(name = "drawer", tabsNavModel = CustomConfiguration(
        content = { DrawerScreen() }
    )) {
        tab(FeedTab()) {
            screen(name = "tab") {
                TabScreen(it as? Int)
            }
        }
        tab(SearchTab()) {
            screen(name = "tab") {
                TabScreen(it as? Int)
            }
        }
        tab(CartTab()) {
            screen(name = "tab") {
                TabScreen(it as? Int)
            }
        }
    }
}
