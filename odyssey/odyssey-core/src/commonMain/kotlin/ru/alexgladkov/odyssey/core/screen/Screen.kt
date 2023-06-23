package ru.alexgladkov.odyssey.core.screen

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import ru.alexgladkov.odyssey.core.CoreScreen
import ru.alexgladkov.odyssey.core.animations.AnimationType

@Immutable
data class Screen(
    override val key: String = "",
    override val realKey: String = "",
    override val params:  Any? = null,
    override val animationType: AnimationType = AnimationType.None,
    override val isForward: Boolean = true
) : ScreenInteractor, CoreScreen {
    override fun toString(): String {
        return "key $key, realKey $realKey, params $params, animationType $animationType, isForward $isForward"
    }
}