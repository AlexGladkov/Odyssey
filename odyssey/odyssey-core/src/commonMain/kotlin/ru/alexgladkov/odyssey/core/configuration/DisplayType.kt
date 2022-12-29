package ru.alexgladkov.odyssey.core.configuration

sealed class DisplayType {
    object EdgeToEdge : DisplayType()
    object FullScreen : DisplayType()
}