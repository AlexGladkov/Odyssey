package ru.alexgladkov.odyssey.core.screen

import androidx.compose.runtime.Stable
import ru.alexgladkov.odyssey.core.animations.AnimationType

@Stable
data class Screen(
    override val key: String = "",
    override val realKey: String = "",
    override val params:  Any? = null,
    override val animationType: AnimationType = AnimationType.None,
    override val isForward: Boolean = true
) : ScreenInteractor {
    override fun toString(): String {
        return "key $key, realKey $realKey, params $params, animationType $animationType, isForward $isForward"
    }
}