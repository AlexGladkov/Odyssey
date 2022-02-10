package ru.alexgladkov.odyssey.core.screen

import ru.alexgladkov.odyssey.core.animations.AnimationType

interface ScreenInteractor {
    val key: String
    val realKey: String
    val params: Any?
    val animationType: AnimationType
    val isForward: Boolean
}