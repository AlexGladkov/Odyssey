package ru.alexgladkov.odyssey.core

import androidx.compose.runtime.Stable
import ru.alexgladkov.odyssey.core.screen.ScreenBundle
import ru.alexgladkov.odyssey.core.screen.ScreenInteractor

@Stable
data class NavConfiguration(
    val screen: ScreenInteractor,
    val screenToRemove: ScreenInteractor? = null
)

fun ScreenInteractor.wrap(): NavConfiguration = NavConfiguration(this)
fun ScreenInteractor.wrap(with: ScreenInteractor): NavConfiguration = NavConfiguration(this, with)
fun ScreenInteractor.toScreenBundle(): ScreenBundle = ScreenBundle(key, realKey, params)