package ru.alexgladkov.common.compose.navigation

import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.odyssey.core.declarative.RootControllerBuilder

fun RootControllerBuilder.generateGraph() {
    destination(NavigationTree.Root.Splash.name)
    flow(NavigationTree.Root.Auth.name) {
        destination(NavigationTree.Auth.Login.name)
        destination(NavigationTree.Auth.TwoFactor.name)
    }
    multistack(NavigationTree.Root.Main.name) {
        flow(NavigationTree.Tabs.Main.name) {
            destination(NavigationTree.Main.Feed.name)
            destination(NavigationTree.Main.Detail.name)
        }

        flow(NavigationTree.Tabs.Favorite.name) {
            destination(NavigationTree.Favorite.Flow.name)
        }

        flow(NavigationTree.Tabs.Settings.name) {
            destination(NavigationTree.Settings.Profile.name)
        }
    }
    destination(NavigationTree.Root.Dialog.name)
}