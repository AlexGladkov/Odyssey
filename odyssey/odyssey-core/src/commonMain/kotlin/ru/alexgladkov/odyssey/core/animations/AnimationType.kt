package ru.alexgladkov.odyssey.core.animations

import ru.alexgladkov.odyssey.core.platform.CommonParcelable
import ru.alexgladkov.odyssey.core.platform.CommonParcelize

/**
 * Animation type is types of animation for navigation transition
 *
 * @see None - provide this if you don't want any animation
 * @see Push - standard ios like push animation, best for inner navigation
 * @see Present - standard ios like present animation, best for modal presentation
 * @see Fade - cross fade animation, fit to everything
 * @see Custom - you can provide your own transition to implement // Not available atm
 *
 * @constructor Create empty Animation typ
 */
@CommonParcelize
sealed class AnimationType : CommonParcelable {
    object None : AnimationType()
    data class Push(val animationTime: Int) : AnimationType()
    data class Present(val animationTime: Int) : AnimationType()
    data class Fade(val animationTime: Int) : AnimationType()
}

fun AnimationType.getAnimationTime(): Int = when (this) {
    is AnimationType.Fade -> animationTime
    AnimationType.None -> 0
    is AnimationType.Present -> animationTime
    is AnimationType.Push -> animationTime
}
