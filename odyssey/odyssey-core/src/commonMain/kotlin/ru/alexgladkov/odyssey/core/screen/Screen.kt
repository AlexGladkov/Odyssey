package ru.alexgladkov.odyssey.core.screen

import ru.alexgladkov.odyssey.core.animations.AnimationType

data class Screen(
    override val key: String = "",
    override val realKey: String = "",
    override val params:  Any? = null,
    override val animationType: AnimationType = AnimationType.None,
    override val isForward: Boolean = true
) : ScreenInteractor