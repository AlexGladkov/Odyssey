package ru.alexgladkov.odyssey.core.breadcrumbs

import androidx.compose.runtime.Stable

@Stable
data class Breadcrumb(
    val absolutePath: String,
    val currentScreen: String,
    val targetScreen: String,
    val controllerName: String
)