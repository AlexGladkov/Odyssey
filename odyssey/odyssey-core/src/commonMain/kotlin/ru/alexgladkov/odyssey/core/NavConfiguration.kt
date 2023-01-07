package ru.alexgladkov.odyssey.core

import ru.alexgladkov.odyssey.core.screen.ScreenBundle
import ru.alexgladkov.odyssey.core.screen.ScreenInteractor

data class NavConfiguration(
    val screen: ScreenInteractor,
    val screensToRemove: List<ScreenInteractor>? = null
)

fun ScreenInteractor.wrap(): NavConfiguration = NavConfiguration(this)
fun ScreenInteractor.replaceSingle(with: ScreenInteractor): NavConfiguration = NavConfiguration(this,listOf(with))
fun ScreenInteractor.replaceMultipleScreens(withScreens: List<ScreenInteractor>): NavConfiguration = NavConfiguration(this, withScreens)
fun ScreenInteractor.toScreenBundle(): ScreenBundle = ScreenBundle(key, realKey, params)

fun  List<ScreenInteractor>.toNavConfigWithScreenReplace(previous: ScreenInteractor, replaceWith: ScreenInteractor): NavConfiguration {
    val screens = this.toMutableList()
    screens.add(previous)
    return replaceWith.replaceMultipleScreens(screens)
}