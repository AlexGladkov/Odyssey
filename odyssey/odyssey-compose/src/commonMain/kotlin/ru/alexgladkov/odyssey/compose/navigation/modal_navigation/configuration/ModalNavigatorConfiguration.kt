package ru.alexgladkov.odyssey.compose.navigation.modal_navigation.configuration

import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.core.configuration.DisplayType

interface ModalNavigatorConfiguration {
    val backgroundColor: Color
    val displayType: DisplayType
}


data class DefaultModalConfiguration(
    override val backgroundColor: Color,
    override val displayType: DisplayType
) : ModalNavigatorConfiguration