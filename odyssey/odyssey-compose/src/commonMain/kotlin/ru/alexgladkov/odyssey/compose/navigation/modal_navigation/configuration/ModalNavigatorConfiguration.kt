package ru.alexgladkov.odyssey.compose.navigation.modal_navigation.configuration

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.configuration.DisplayType

@Stable
interface ModalNavigatorConfiguration {
    val backgroundColor: Color
    val displayType: DisplayType
}

@Immutable
data class DefaultModalConfiguration(
    override val backgroundColor: Color,
    override val displayType: DisplayType,
) : ModalNavigatorConfiguration