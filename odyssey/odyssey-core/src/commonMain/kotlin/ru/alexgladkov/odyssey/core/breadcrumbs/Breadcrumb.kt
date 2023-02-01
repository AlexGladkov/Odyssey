package ru.alexgladkov.odyssey.core.breadcrumbs

data class Breadcrumb(
    val absolutePath: String,
    val currentScreen: String,
    val targetScreen: String,
    val controllerName: String
)