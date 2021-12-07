package ru.alexgladkov.odyssey.compose.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.tween

/**
 * Provide presentation transition
 *
 * @param T - transition target state
 * @param isOpen - animation direction (true for forward, false for backward)
 * @param transitionTime - animation duration
 */
@OptIn(ExperimentalAnimationApi::class)
fun <T> providePresentationTransition(
    isOpen: Boolean,
    transitionTime: Int
): AnimatedContentScope<T>.() -> ContentTransform = {
    if (isOpen) {
        // Forward animation
        (slideInVertically(
            animationSpec = tween(transitionTime),
            initialOffsetY = { height -> height })
                + fadeIn(animationSpec = tween(transitionTime)) with
                slideOutVertically(
                    animationSpec = tween(transitionTime),
                    targetOffsetY = { height -> -(height / 8) })
                + fadeOut(animationSpec = tween(transitionTime)))
            .using(
                SizeTransform(clip = false)
            )
    } else {
        (slideInVertically(
            animationSpec = tween(transitionTime),
            initialOffsetY = { height -> -(height / 8) })
                + fadeIn(animationSpec = tween(transitionTime)) with
                slideOutVertically(
                    animationSpec = tween(transitionTime), targetOffsetY = { height -> height })
                + fadeOut(animationSpec = tween(transitionTime))
                )
            .using(
                SizeTransform(clip = false)
            )
    }
}

/**
 * Provide push transition
 *
 * @param T - transition target state
 * @param isForward - animation direction (true for forward, false for backward)
 * @param transitionTime - animation duration
 */
@OptIn(ExperimentalAnimationApi::class)
fun <T> providePushTransition(isForward: Boolean, transitionTime: Int): AnimatedContentScope<T>.() -> ContentTransform =
    {
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
    }

@OptIn(ExperimentalAnimationApi::class)
fun <T> provideCrossFadeTransition(transitionTime: Int): AnimatedContentScope<T>.() -> ContentTransform = {
    (fadeIn(animationSpec = tween(transitionTime)) with fadeOut(animationSpec = tween(transitionTime)))
        .using(SizeTransform(clip = false))
}
