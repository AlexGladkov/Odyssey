package ru.alexgladkov.odyssey.core

import ru.alexgladkov.odyssey.core.screen.ScreenInteractor

data class NavConfiguration(
    val screen: ScreenInteractor,
    val removeScreen: String? = null
)

fun ScreenInteractor.wrap(): NavConfiguration = NavConfiguration(this)
fun ScreenInteractor.wrap(with: String): NavConfiguration = NavConfiguration(this, with)
