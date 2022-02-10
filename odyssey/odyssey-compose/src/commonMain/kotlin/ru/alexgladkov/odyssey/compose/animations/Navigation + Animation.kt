package ru.alexgladkov.odyssey.compose.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import ru.alexgladkov.odyssey.core.animations.AnimationType

/**
 * Animated transition
 *
 * @param targetState - compose state to observe animation
 * @param animation - type of animations (@see AnimationType for more)
 * @param isForwardDirection - animation direction (true for forward, false for backward)
 * @param onAnimationEnd - animation end callback
 * @param content - composable to animate
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <T> AnimatedTransition(
    targetState: T,
    animation: AnimationType,
    isForwardDirection: Boolean,
    onAnimationEnd: (() -> Unit)? = null,
    content: @Composable (T) -> Unit
) {
    AnimatedNavigation(
        targetState = targetState,
        transitionSpec = when (animation) {
            is AnimationType.Present -> providePresentationTransition(isForwardDirection, animation.animationTime)
            is AnimationType.Fade -> provideCrossFadeTransition(animation.animationTime)
            AnimationType.None -> providePresentationTransition(isForwardDirection, 1)
            is AnimationType.Push -> providePushTransition(isForwardDirection, animation.animationTime)
        },
        onAnimationEnd = onAnimationEnd,
        content = content
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <T> AnimatedNavigation(
    targetState: T,
    transitionSpec: AnimatedContentScope<T>.() -> ContentTransform,
    onAnimationEnd: (() -> Unit)? = null,
    content: @Composable (T) -> Unit
) {
    AnimatedContentWithCallback(
        targetState = targetState,
        transitionSpec = transitionSpec,
        onAnimationEnd = onAnimationEnd ?: { }
    ) { target ->
        content(target)
    }
}