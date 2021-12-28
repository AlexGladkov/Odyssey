package ru.alexgladkov.common.compose.navigation

import ru.alexgladkov.common.compose.screens.CitiesScreen
import ru.alexgladkov.common.compose.screens.MainScreen
import ru.alexgladkov.common.compose.screens.OnboardingScreen
import ru.alexgladkov.common.compose.screens.StartScreen
import ru.alexgladkov.common.compose.tabs.BottomConfiguration
import ru.alexgladkov.common.compose.tabs.CartTab
import ru.alexgladkov.common.compose.tabs.FeedTab
import ru.alexgladkov.common.compose.tabs.SearchTab
import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.multistack
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.helpers.screen
import ru.alexgladkov.odyssey.compose.helpers.tab
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.generateGraph() {
    screen(name = "start") {
        StartScreen()
    }

    screen(name = "cities") {
        CitiesScreen()
    }

    onboardingFlow()
    mainScreen()
}

fun RootComposeBuilder.mainScreen() {
    multistack(name = "main", bottomNavModel = BottomConfiguration()) {
        tab(FeedTab())
        tab(SearchTab())
        tab(CartTab())
    }
}

fun RootComposeBuilder.onboardingFlow() {
    flow(name = "onboarding") {
        screen(name = "onboarding") {
            OnboardingScreen(count = it as Int)
        }
    }
}