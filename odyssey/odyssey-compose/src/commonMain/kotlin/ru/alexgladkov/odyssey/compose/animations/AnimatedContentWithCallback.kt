package ru.alexgladkov.odyssey.compose.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Overriden AnimatedContent function from core Compose
 * Contains End Animation Callback
 *
 * @param targetState - observed state
 * @param modifier
 * @param transitionSpec - provide animation and transformation
 * @param contentAlignment
 * @param onAnimationEnd - calls when animation end
 * @param content
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <S> AnimatedContentWithCallback(
    targetState: S,
    modifier: Modifier = Modifier,
    transitionSpec: AnimatedContentScope<S>.() -> ContentTransform,
    contentAlignment: Alignment = Alignment.TopStart,
    onAnimationEnd: () -> Unit,
    content: @Composable AnimatedVisibilityScope.(targetState: S) -> Unit
) {
    var previousValue by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = targetState, label = "AnimatedContent")

    if (transition.isRunning != previousValue) {
        previousValue = transition.isRunning
        if (!transition.isRunning) {
            onAnimationEnd()
        }
    }

    transition.AnimatedContent(
        modifier,
        transitionSpec,
        contentAlignment,
        content = content
    )
}