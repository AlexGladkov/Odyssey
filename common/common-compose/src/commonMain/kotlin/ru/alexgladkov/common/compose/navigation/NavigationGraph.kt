package ru.alexgladkov.common.compose.navigation

import ru.alexgladkov.common.compose.screens.*
import ru.alexgladkov.common.compose.tabs.BottomConfiguration
import ru.alexgladkov.common.compose.tabs.CartTab
import ru.alexgladkov.common.compose.tabs.FeedTab
import ru.alexgladkov.common.compose.tabs.SearchTab
import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.bottomNavigation
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.extensions.tab
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.generateGraph() {
    screen(name = "start") {
        StartScreen()
    }

    screen(name = "cities") {
        CitiesScreen()
    }

    screen(name = "checkout") {
        CheckoutScreen()
    }

    onboardingFlow()
    mainScreen()
}

fun RootComposeBuilder.mainScreen() {
    bottomNavigation(name = "main", bottomNavModel = BottomConfiguration()) {
        tab(FeedTab()) {
            screen(name = "feed") {
                FeedScreen()
            }
        }
        tab(SearchTab()) {
            screen(name = "search") {
                SearchScreen()
            }

            screen(name = "product") {
                ProductScreen()
            }
        }
        tab(CartTab()) {
            screen(name = "cart") {
                CartScreen()
            }
        }
    }
}

fun RootComposeBuilder.onboardingFlow() {
    flow(name = "onboarding") {
        screen(name = "onboarding_start") {
            OnboardingScreen(count = it as Int)
        }
    }
}