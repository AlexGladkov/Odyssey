package ru.alexgladkov.odyssey.compose.setup

sealed class StartScreen {
    object First : StartScreen()
    data class Custom(val startName: String) : StartScreen()
}

expect class OdysseyConfiguration