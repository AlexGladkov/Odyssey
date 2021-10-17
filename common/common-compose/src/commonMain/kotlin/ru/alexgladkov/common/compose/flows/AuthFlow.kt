package ru.alexgladkov.common.compose.flows

import androidx.compose.runtime.Composable
import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.common.compose.screens.LoginCodeScreen
import ru.alexgladkov.common.compose.screens.LoginScreen
import ru.alexgladkov.odyssey.compose.extensions.observeAsState
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.controllers.FlowRootController

@Composable
fun AuthFlow(rootController: RootController) {
    val flowRootController = rootController as FlowRootController
    val navigation = flowRootController.backStackObserver.observeAsState()

    navigation.value?.let { entry ->
        when (entry.destination.destinationName()) {
            NavigationTree.Auth.Login.toString() -> LoginScreen(entry.rootController)
            NavigationTree.Auth.TwoFactor.toString() -> LoginCodeScreen(entry.rootController)
        }
    }
}