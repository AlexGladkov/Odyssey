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

//fun RootComposeBuilder.topNavScreen() {
//    topNavigation(name = "top", tabsNavModel = TopConfiguration()) {
//        tab(FeedTab()) {
//            screen(name = "top.feed") {
//                FeedScreen()
//            }
//        }
//        tab(SearchTab()) {
//            screen(name = "top.search") {
//                SearchScreen()
//            }
//
//            screen(name = "top.product") {
//                ProductScreen()
//            }
//        }
//        tab(CartTab()) {
//            screen(name = "top.cart") {
//                CartScreen()
//            }
//        }
//    }
//}

//fun RootComposeBuilder.customNavScreen() {
//    customNavigation(name = "custom", tabsNavModel = CustomConfiguration(
//        content = { CustomNavScreen() }
//    )) {
//        tab(FeedTab()) {
//            screen(name = "custom.feed") {
//                FeedScreen()
//            }
//        }
//        tab(SearchTab()) {
//            screen(name = "custom.search") {
//                SearchScreen()
//            }
//
//            screen(name = "custom.product") {
//                ProductScreen()
//            }
//        }
//        tab(CartTab()) {
//            screen(name = "custom.cart") {
//                CartScreen()
//            }
//        }
//    }
//}
//
//fun RootComposeBuilder.onboardingFlow() {
//    flow(name = "onboarding") {
//        screen(name = "onboarding_start") {
//            OnboardingScreen(count = it as Int)
//        }
//
//        screen(name = "onboarding_second") {
//            OnboardingSecondScreen(it as String)
//        }
//    }
//}