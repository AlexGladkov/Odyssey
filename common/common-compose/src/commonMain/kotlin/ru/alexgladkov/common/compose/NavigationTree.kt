package ru.alexgladkov.common.compose

import ru.alexgladkov.odyssey.core.NavigationScreen

object NavigationTree {
    enum class Root : NavigationScreen {
        Start, Container
    }

    enum class Container : NavigationScreen {
        Chain
    }

    enum class Tabs : NavigationScreen {
        Main, Favorite, Settings
    }
}