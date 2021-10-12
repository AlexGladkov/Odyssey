package ru.alexgladkov.common.compose.navigation

import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.destination.Destination
import ru.alexgladkov.odyssey.core.destination.DestinationFlow
import ru.alexgladkov.odyssey.core.destination.DestinationMultiFlow
import ru.alexgladkov.odyssey.core.destination.DestinationScreen

fun RootController.generateNavigationGraph() {
    // TODO - Remove this to declarative creation
    generateDestinations(ArrayList<Destination>().apply {
        add(DestinationScreen(NavigationTree.Root.Splash.toString()))
        add(
            DestinationFlow(
                name = NavigationTree.Root.Auth.toString(),
                screens = ArrayList<DestinationScreen>().apply {
                    add(DestinationScreen(NavigationTree.Auth.Login.toString()))
                    add(DestinationScreen(NavigationTree.Auth.TwoFactor.toString()))
                })
        )
        add(
            DestinationMultiFlow(
                name = NavigationTree.Root.Main.toString(),
                flows = ArrayList<DestinationFlow>().apply {
                    add(DestinationFlow(
                        name = NavigationTree.Tabs.Main.toString(),
                        screens = ArrayList<DestinationScreen>().apply {
                            add(DestinationScreen(NavigationTree.Main.Feed.toString()))
                            add(DestinationScreen(NavigationTree.Main.Detail.toString()))
                        }
                    ))

                    add(DestinationFlow(
                        name = NavigationTree.Tabs.Favorite.toString(),
                        screens = ArrayList<DestinationScreen>().apply {
                            add(DestinationScreen(NavigationTree.Favorite.Flow.toString()))
                        }
                    ))

                    add(DestinationFlow(
                        name = NavigationTree.Tabs.Settings.toString(),
                        screens = ArrayList<DestinationScreen>().apply {
                            add(DestinationScreen(NavigationTree.Settings.Profile.toString()))
                        }
                    ))
                }
            )
        )

        add(DestinationScreen(NavigationTree.Root.Dialog.toString()))
    })
}