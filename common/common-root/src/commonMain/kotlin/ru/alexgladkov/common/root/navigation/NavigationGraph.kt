package ru.alexgladkov.common.root.navigation

import ru.alexgladkov.common.root.screens.*
import ru.alexgladkov.common.root.tabs.BottomConfiguration
import ru.alexgladkov.common.root.tabs.CartTab
import ru.alexgladkov.common.root.tabs.FeedTab
import ru.alexgladkov.common.root.tabs.SearchTab
import ru.alexgladkov.odyssey.core.extensions.bottomNavigation
import ru.alexgladkov.odyssey.core.extensions.flow
import ru.alexgladkov.odyssey.core.extensions.screen
import ru.alexgladkov.odyssey.core.extensions.tab
import ru.alexgladkov.odyssey.core.navigation.RootComposeBuilder

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