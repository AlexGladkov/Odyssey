package ru.alexgladkov.odyssey.compose.setup

import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.core.configuration.DisplayType

actual class OdysseyConfiguration(
    val canvas: ComponentActivity,
    val startScreen: StartScreen = StartScreen.First,
    val backgroundColor: Color = Color.White,
    val displayType: DisplayType = DisplayType.EdgeToEdge
)