package ru.alexgladkov.odyssey.core.breadcrumbs

sealed class Breadcrumb {
    data class SimpleNavigation(val startScreen: String, val targetScreen: String) : Breadcrumb()
    data class TabSwitch(val startPosition: Int, val targetPosition: Int) : Breadcrumb()
    data class TabNavigation(
        val tabName: String,
        val startScreen: String,
        val targetScreen: String
    ) : Breadcrumb()

    data class ModalNavigation(val screenKey: String) : Breadcrumb()
}