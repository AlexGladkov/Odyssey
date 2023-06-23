package ru.alexgladkov.odyssey.compose.base

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import ru.alexgladkov.odyssey.compose.animations.AnimatedTransition
import ru.alexgladkov.odyssey.core.CoreScreen
import ru.alexgladkov.odyssey.core.animations.AnimationType

@Composable
fun AnimatedHost(
    currentScreen: CoreScreen,
    screenToRemove: CoreScreen?,
    animationType: AnimationType,
    isForward: Boolean,
    modifier: Modifier = Modifier,
    onScreenRemove: ((CoreScreen) -> Unit)? = null,
    content: @Composable (CoreScreen) -> Unit
) {
    val saveableStateHolder = rememberSaveableStateHolder()

    Box(modifier) {
        AnimatedTransition(
            targetState = currentScreen,
            animation = animationType,
            isForwardDirection = isForward
        ) {
            val key = remember { currentScreen.key }
            saveableStateHolder.SaveableStateProvider(key) {
                content(currentScreen)
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