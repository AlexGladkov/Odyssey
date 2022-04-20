package ru.alexgladkov.odyssey.compose.navigation.modal_navigation

import ru.alexgladkov.odyssey.compose.Render

/**
 * Class configurator for modal sheet controller
 * @param maxHeight - maxHeight in Float. use null for wrap by content
 * @param threshold - threshold to close modal bottom sheet by swipe
 * @param cornerRadius - card corner radius in dp
 * @param alpha - scrimer alpha
 * @param closeOnBackdropClick - true if you want to close on backdrop click
 * @param backContent - draw behind modal view (composable)
 */
data class ModalSheetConfiguration(
    override val animationTime: Int = 400,
    val maxHeight: Float? = null,
    val threshold: Float = 0.3f,
    val cornerRadius: Int = 0,
    val alpha: Float = 0.2f,
    val closeOnBackdropClick: Boolean = true,
    val closeOnSwipe: Boolean = true,
    val backContent: Render? = null,
) : ModalConfiguration

data class AlertConfiguration(
    override val animationTime: Int = 400,
    val maxHeight: Float? = null,
    val maxWidth: Float? = null,
    val cornerRadius: Int = 0,
    val alpha: Float = 0.2f,
    val closeOnBackdropClick: Boolean = true
) : ModalConfiguration

data class CustomModalConfiguration(
    override val animationTime: Int = 400
) : ModalConfiguration

interface ModalConfiguration {
    val animationTime: Int
}