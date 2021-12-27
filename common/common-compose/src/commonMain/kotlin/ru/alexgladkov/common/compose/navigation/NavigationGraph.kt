package ru.alexgladkov.common.compose.navigation

import ru.alexgladkov.common.compose.*
import ru.alexgladkov.common.compose.screens.CitiesScreen
import ru.alexgladkov.common.compose.screens.OnboardingScreen
import ru.alexgladkov.common.compose.screens.StartScreen
import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.helpers.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.generateGraph() {
    screen(name = "start") {
        StartScreen()
    }

    screen(name = "cities") {
        CitiesScreen()
    }

    onboardingFlow()
}

fun RootComposeBuilder.onboardingFlow() {
    flow(name = "onboarding") {
        screen(name = "onboarding") {
            OnboardingScreen(count = 1)
        }
    }
}