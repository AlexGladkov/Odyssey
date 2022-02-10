package ru.alexgladkov.odyssey.core.screen

data class ScreenBundle(
    val key: String,
    val realKey: String? = null,
    val params: Any?
)