package ru.alexgladkov.odyssey.compose.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.alexgladkov.odyssey.compose.animations.AnimatedTransition
import ru.alexgladkov.odyssey.compose.helpers.BottomSheetBundle
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.screen.ScreenBundle

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimatedHost(
    currentScreen: ScreenBundle,
    removeScreen: String?,
    animationType: AnimationType,
    isForward: Boolean,
    modifier: Modifier = Modifier,
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

    LaunchedEffect(currentScreen) {
        removeScreen?.let { saveableStateHolder.removeState(removeScreen) }
    }
}