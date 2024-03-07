package ru.alexgladkov.odyssey.compose.base

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import ru.alexgladkov.odyssey.compose.animations.AnimatedTransition
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.screen.ScreenBundle

@Composable
fun AnimatedHost(
    currentScreen: ScreenBundle,
    screenToRemove: ScreenBundle?,
    animationType: AnimationType,
    isForward: Boolean,
    modifier: Modifier = Modifier,
    onScreenRemove: ((ScreenBundle) -> Unit)? = null,
    content: @Composable (ScreenBundle) -> Unit
) {
    val saveableStateHolder = rememberSaveableStateHolder()

    Box(modifier) {
        AnimatedTransition(
            targetState = currentScreen,
            animation = animationType,
            isForwardDirection = isForward
        ) {
            saveableStateHolder.SaveableStateProvider(it.key) {
                content(it)
            }
        }
    }

    LaunchedEffect(currentScreen, screenToRemove) {
        screenToRemove?.let {
            saveableStateHolder.removeState(it.key)
            onScreenRemove?.invoke(it)
        }
    }
}