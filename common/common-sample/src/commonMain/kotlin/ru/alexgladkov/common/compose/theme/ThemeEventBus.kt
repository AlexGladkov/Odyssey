package ru.alexgladkov.common.compose.theme

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ThemeSettings(
    val isDarkTheme: Boolean = true
)

class ThemeEventBus {

    private val _themeState: MutableStateFlow<ThemeSettings> = MutableStateFlow(ThemeSettings())
    val themeState: StateFlow<ThemeSettings> = _themeState

    fun switchTheme() {
        _themeState.value = _themeState.value.copy(isDarkTheme = !_themeState.value.isDarkTheme)
    }
}

val LocalTheme = staticCompositionLocalOf { ThemeEventBus() }