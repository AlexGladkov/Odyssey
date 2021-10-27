package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.runtime.Composable

typealias ScreenMap = Map<String, @Composable ScreenBundle.() -> Unit>
typealias MutableScreenMap = MutableMap<String, @Composable ScreenBundle.() -> Unit>