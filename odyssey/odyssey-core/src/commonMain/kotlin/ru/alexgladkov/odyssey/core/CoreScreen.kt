package ru.alexgladkov.odyssey.core

import androidx.compose.runtime.Stable

@Stable
interface CoreScreen {
    val key: String
    val realKey: String?
    val params: Any?
}