package ru.alexgladkov.common.compose.sample_lmwork

import ru.alexgladkov.common.compose.sample_lmwork.screens.AuthScreen
import ru.alexgladkov.common.compose.sample_lmwork.screens.AuthWebScreen
import ru.alexgladkov.common.compose.sample_lmwork.screens.SplashScreen
import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.helpers.FlowBuilder
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.generateLmWorkGraph() {
    screen(NavigationTree.Root.Splash.name) {
        SplashScreen()
    }

    flow(name = NavigationTree.Root.Auth.name) {
        loginScreen()
        loginWebScreen()
    }
}

fun FlowBuilder.loginScreen() {
    screen(name = NavigationTree.Auth.Login.name) {
        AuthScreen()
    }
}

fun FlowBuilder.loginWebScreen() {
    screen(name = NavigationTree.Auth.LoginWeb.name) {
        AuthWebScreen()
    }
}
