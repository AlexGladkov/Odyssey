package ru.alexgladkov.odyssey.navigation

import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.testNavigationGraph() {
    screen("screen 1") {

    }

    screen("screen 2") {

    }

    screen("screen 3") {

    }

    screen("screen 4") {

    }

    screen("screen 5") {

    }

    screen("screen 6") {

    }

    flow("flow 1") {
        screen("flow screen 1") {

        }

        screen("flow screen 2") {

        }

        screen("flow screen 3") {

        }
    }

    screen("screen 7") {

    }
}