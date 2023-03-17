package ru.alexgladkov.odyssey.compose.navigation.modal_navigation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import ru.alexgladkov.odyssey.compose.Render
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.animations.defaultFadeAnimation
import ru.alexgladkov.odyssey.core.animations.defaultPresentationAnimation

/**
 * Class configurator for modal sheet controller
 * @param animationTime (deprecated) - time to animation display
 * @param animationType - type of appearing animation
 * @param maxHeight - maxHeight in Float. use null for wrap by content
 * @param threshold - threshold to close modal bottom sheet by swipe
 * @param cornerRadius - card corner radius in dp
 * @param alpha - scrimer alpha
 * @param closeOnBackdropClick - true if you want to close on backdrop click
 * @param closeOnSwipe - true if you want to close on swipe
 * @param closeOnBackClick - true if you want to close on the hardware back button
 * @param backContent - draw behind modal view (composable)
 */
@Immutable
data class ModalSheetConfiguration(
    @Deprecated("No need to provide animation time more, use instead animation type")
    override val animationTime: Int = 400,
    override val animationType: AnimationType = defaultPresentationAnimation(),
    val maxHeight: Float? = null,
    val threshold: Float = 0.3f,
    val cornerRadius: Int = 0,
    val alpha: Float = 0.2f,
    val closeOnBackdropClick: Boolean = true,
    val closeOnSwipe: Boolean = true,
    val closeOnBackClick: Boolean = true,
    val backContent: Render? = null,
) : ModalConfiguration

/**
 * Configuration for alert dialog
 * @param animationTime (deprecated) - time to animation display
 * @param animationType - type of appearing animation
 * @param maxHeight - maximum height in percents
 * @param maxWidth - maximum width in percents
 * @param cornerRadius - corner radius in dp (Int)
 * @param alpha - back screamer alpha
 * @param closeOnBackdropClick - true for outside clickable exit
 * @param closeOnBackClick - true for hardware backpress exit
 */
@Immutable
data class AlertConfiguration(
    @Deprecated("No need to provide animation time more, use instead animation type")
    override val animationTime: Int = 400,
    override val animationType: AnimationType = defaultFadeAnimation(),
    val maxHeight: Float? = null,
    val maxWidth: Float? = null,
    val cornerRadius: Int = 0,
    val alpha: Float = 0.2f,
    val closeOnBackdropClick: Boolean = true,
    val closeOnBackClick: Boolean = true,
) : ModalConfiguration

/**
 * Configuration for custom modal forms
 * @param animationTime (deprecated) - time to animation display
 * @param animationType - type of appearing animation
 */
@Immutable
data class CustomModalConfiguration(
    @Deprecated("No need to provide animation time more, use instead animation type")
    override val animationTime: Int = 400,
    override val animationType: AnimationType
) : ModalConfiguration

@Stable
interface ModalConfiguration {
    val animationTime: Int
    val animationType: AnimationType
}