package ru.alexgladkov.odyssey.core

import androidx.compose.runtime.Immutable
import ru.alexgladkov.odyssey.core.screen.ScreenBundle
import ru.alexgladkov.odyssey.core.screen.ScreenInteractor

@Immutable
data class NavConfiguration(
    val screen: ScreenInteractor,
    val screenToRemove: ScreenInteractor? = null
)

fun ScreenInteractor.wrap(): NavConfiguration = NavConfiguration(this)
fun ScreenInteractor.wrap(with: ScreenInteractor): NavConfiguration = NavConfiguration(this, with)
fun ScreenInteractor.toCoreScreen(): CoreScreen = ScreenBundle(key, realKey, params)