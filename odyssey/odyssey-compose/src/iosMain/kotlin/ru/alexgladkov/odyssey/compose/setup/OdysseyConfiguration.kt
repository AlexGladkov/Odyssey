package ru.alexgladkov.odyssey.compose.setup

import androidx.compose.ui.graphics.Color

/**
 * Actual configuration for ios main target
 * @param startScreen (optional) - sealed class with two types of params First and Custom.
 * @param backgroundColor (optional) - color for animation background. For example if you have dark theme you may want
 */
actual class OdysseyConfiguration(
    val startScreen: StartScreen = StartScreen.First,
    val backgroundColor: Color = Color.White
)