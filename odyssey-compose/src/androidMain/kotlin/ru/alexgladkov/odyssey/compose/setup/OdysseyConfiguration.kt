package ru.alexgladkov.odyssey.compose.setup

import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import ru.alexgladkov.odyssey.core.configuration.DisplayType

/**
 * @param canvas (required) - Host for backpress setup and other android components
 * @param startScreen (optional) - sealed class with two types of params First and Custom.
 * if you check first, which is by default, it will take first screen from your navigation graph, otherwise
 * it takes as entry point screen with provided name
 * @param backgroundColor (optional) - color for animation background. For example if you have dark theme you may want
 * to provide your theme color to avoid blink effect in animations
 * @param navigationBarColor (optional) - set color for system navigation bar component if DisplayType.FullScreen selected
 * @param statusBarColor (optional) - set color for system status bar component if DisplayType.FullScreen selected
 * @param displayType (optional) - set display type for navigation host. Choose EdgeToEdge if you want place host between status bar and navigation bar
 * Choose FullScreen if you want to control status bar and navigation bar color and want modal navigator dim status bar
 */
actual class OdysseyConfiguration(
    val canvas: ComponentActivity,
    val startScreen: StartScreen = StartScreen.First,
    val backgroundColor: Color = Color.White,
    val navigationBarColor: Int = Color.Transparent.toArgb(),
    val statusBarColor: Int = Color.Transparent.toArgb(),
    val displayType: DisplayType = DisplayType.EdgeToEdge
)