package ru.alexgladkov.odyssey.compose.navigation.modal_navigation.configuration

import androidx.compose.ui.graphics.Color

interface ModalNavigatorConfiguration {
    val statusBarColor: Color
}


data class DefaultModalConfiguration(
    override val statusBarColor: Color = Color.Black,
): ModalNavigatorConfiguration