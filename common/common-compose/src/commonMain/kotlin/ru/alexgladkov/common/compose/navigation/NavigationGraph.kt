package ru.alexgladkov.common.compose.navigation

import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.common.compose.screens.*
import ru.alexgladkov.odyssey.compose.extensions.bottomNavigation
import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.multistack
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.helpers.BottomItemModel
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.screen
import ru.alexgladkov.odyssey.compose.navigation.tab

fun buildComposeNavigationGraph(): RootComposeBuilder.() -> Unit {
    return { generateGraph() }
}

fun RootComposeBuilder.generateGraph() {
    screen(NavigationTree.Root.Splash.toString()) {
        SplashScreen(rootController)
    }

    flow(name = NavigationTree.Root.Auth.toString()) {
        screen(NavigationTree.Auth.Onboarding.toString()) {
            OnboardingScreen(rootController)
        }

        screen(NavigationTree.Auth.Login.toString()) {
            LoginScreen(rootController, params as? String)
        }

        screen(NavigationTree.Auth.TwoFactor.toString()) {
            LoginCodeScreen(rootController, params as? String)
        }
    }

    bottomNavigation(
        name = NavigationTree.Root.Main.toString(),
        bottomItemModels = listOf(
            BottomItemModel(title = "Main"),
            BottomItemModel(title = "Favorite"),
            BottomItemModel(title = "Settings")
        )
    ) {
        tab(NavigationTree.Tabs.Main.toString()) {
            screen(NavigationTree.Main.Feed.toString()) {
                FeedScreen(rootController)
            }

            screen(NavigationTree.Main.Detail.toString()) {
                DetailScreen(rootController, param = params as DetailParams)
            }
        }

        tab(NavigationTree.Tabs.Favorite.toString()) {
            screen(NavigationTree.Favorite.Flow.toString()) {
                FavoriteScreen(rootController)
            }
        }

        tab(NavigationTree.Tabs.Settings.toString()) {
            screen(NavigationTree.Settings.Profile.toString()) {
                ProfileScreen()
            }
        }
    }

    screen(NavigationTree.Root.Dialog.toString()) {
        DialogScreen(rootController, params = params as DialogParams)
    }
}