package ru.alexgladkov.odyssey.core.animations

import ru.alexgladkov.odyssey.core.animations.AnimationType

fun defaultPresentationAnimation() = AnimationType.Present(500)
fun defaultPushAnimation() = AnimationType.Push(500)
fun defaultFadeAnimation() = AnimationType.Fade(500)