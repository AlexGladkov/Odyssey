package ru.alexgladkov.odyssey.core.configuration

import androidx.compose.ui.graphics.Color

sealed class DisplayType {
    object EdgeToEdge : DisplayType()
    data class FullScreen(val statusBarColor: Color) : DisplayType()
}