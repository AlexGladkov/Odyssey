package ru.alexgladkov.odyssey.compose.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable

enum class AnimationType {
    Push, Present, Fade
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <T> AnimatedPush(
    targetState: T,
    transitionTime: Int,
    isForward: Boolean,
    onAnimationEnd: (() -> Unit)? = null,
    content: @Composable T.() -> Unit
) {
    AnimatedNavigation(
        targetState = targetState,
        transitionSpec = {
            if (isForward) {
                // Forward animation
                (slideInHorizontally(
                    animationSpec = tween(transitionTime),
                    initialOffsetX = { width -> width })
                        + fadeIn(animationSpec = tween(transitionTime)) with
                        slideOutHorizontally(
                            animationSpec = tween(transitionTime),
                            targetOffsetX = { width -> -width })
                        + fadeOut(animationSpec = tween(transitionTime)))
                    .using(
                        SizeTransform(clip = false)
                    )
            } else {
                (slideInHorizontally(animationSpec = tween(transitionTime), initialOffsetX = { width -> -width })
                        + fadeIn(animationSpec = tween(transitionTime)) with
                        slideOutHorizontally(
                            animationSpec = tween(transitionTime), targetOffsetX = { width -> width })
                        + fadeOut(animationSpec = tween(transitionTime))
                        )
                    .using(
                        SizeTransform(clip = false)
                    )
            }
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
    content: @Composable T.() -> Unit
) {
    AnimatedContentWithCallback(
        targetState = targetState,
        transitionSpec = transitionSpec,
        onAnimationEnd = onAnimationEnd ?: { }
    ) { target ->
        content(target)
    }
}