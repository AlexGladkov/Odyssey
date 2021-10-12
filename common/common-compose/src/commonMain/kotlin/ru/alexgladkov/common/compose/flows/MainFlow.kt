package ru.alexgladkov.common.compose.flows

import androidx.compose.runtime.Composable
import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.common.compose.screens.DetailScreen
import ru.alexgladkov.common.compose.screens.FeedScreen
import ru.alexgladkov.common.compose.screens.LoginCodeScreen
import ru.alexgladkov.common.compose.screens.LoginScreen
import ru.alexgladkov.odyssey.compose.extensions.observeAsState
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.controllers.FlowRootController
import ru.alexgladkov.odyssey.core.destination.DestinationScreen

@Composable
fun MainFlow(rootController: RootController) {
    val flowRootController = rootController as FlowRootController
    val navigation = flowRootController.backStackObserver.observeAsState()
    val params = (navigation.value?.destination as? DestinationScreen)?.params

    navigation.value?.let { entry ->
        when (entry.destination.destinationName()) {
            NavigationTree.Main.Feed.toString() -> FeedScreen(entry.rootController)
            NavigationTree.Main.Detail.toString() -> DetailScreen(entry.rootController, params as String)
        }
    }
}