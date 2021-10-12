package ru.alexgladkov.common.compose.flows

import androidx.compose.runtime.Composable
import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.common.compose.screens.*
import ru.alexgladkov.odyssey.compose.extensions.observeAsState
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.controllers.FlowRootController

@Composable
fun FavoriteFlow(rootController: RootController) {
    val flowRootController = rootController as FlowRootController
    val navigation = flowRootController.backStackObserver.observeAsState()

    navigation.value?.let { entry ->
        when (entry.destination.destinationName()) {
            NavigationTree.Favorite.Flow.toString() -> FavoriteScreen(rootController)
        }
    }
}