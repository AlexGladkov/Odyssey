package ru.alexgladkov.odyssey.compose.navigation.modal_navigation.configuration

import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.core.configuration.DisplayType

interface ModalNavigatorConfiguration {
    val statusBarColor: Color
    val displayType: DisplayType
}


data class DefaultModalConfiguration(
    override val statusBarColor: Color = Color.Black,
    override val displayType: DisplayType = DisplayType.FullScreen
) : ModalNavigatorConfiguration