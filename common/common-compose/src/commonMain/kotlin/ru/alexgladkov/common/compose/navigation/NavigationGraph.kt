package ru.alexgladkov.common.compose.navigation

import ru.alexgladkov.common.compose.screens.*
import ru.alexgladkov.common.compose.tabs.*
import ru.alexgladkov.odyssey.compose.extensions.*
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.mainScreen() {
    bottomNavigation(name = "main", tabsNavModel = BottomConfiguration()) {
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
        content = { params -> DrawerScreen(params) }
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
