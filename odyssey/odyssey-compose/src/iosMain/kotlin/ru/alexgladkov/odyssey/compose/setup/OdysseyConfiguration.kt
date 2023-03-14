package ru.alexgladkov.odyssey.compose.setup

import androidx.compose.ui.graphics.Color

actual class OdysseyConfiguration(
    val startScreen: StartScreen = StartScreen.First,
    val backgroundColor: Color = Color.White
)