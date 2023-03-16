package ru.alexgladkov.odyssey.core.screen

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
data class ScreenBundle(
    val key: String,
    val realKey: String? = null,
    val params: Any?
)