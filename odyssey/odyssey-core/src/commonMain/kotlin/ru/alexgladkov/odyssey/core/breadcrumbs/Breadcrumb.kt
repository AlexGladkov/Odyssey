package ru.alexgladkov.odyssey.core.breadcrumbs

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
data class Breadcrumb(
    val absolutePath: String,
    val currentScreen: String,
    val targetScreen: String,
    val controllerName: String
)