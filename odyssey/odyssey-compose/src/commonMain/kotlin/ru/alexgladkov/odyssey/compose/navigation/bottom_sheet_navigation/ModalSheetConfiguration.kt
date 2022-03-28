package ru.alexgladkov.odyssey.compose.navigation.bottom_sheet_navigation

import ru.alexgladkov.odyssey.compose.Render

/**
 * Class configurator for modal sheet controller
 * @param maxHeight - maxHeight in Float. use null for wrap by content
 * @param cornerRadius - card corner radius in dp
 * @param alpha - scrimer alpha
 * @param closeOnBackdropClick - true if you want to close on backdrop click
 * @param backContent - composable for backdrop content
 */
data class ModalSheetConfiguration(
    val maxHeight: Float? = null,
    val cornerRadius: Int = 0,
    val alpha: Float = 0.2f,
    val closeOnBackdropClick: Boolean = true,
    val backContent: Render? = null,
)

/**
 * Class configurator for modal sheet controller
 * @param maxHeight - maxHeight in percent. Use null for wrap by content
 * @param maxWidth - maxWidth in percent. Use null for wrap by content
 * @param cornerRadius - card corner radius in dp
 * @param alpha - scrimer alpha
 * @param closeOnBackdropClick - true if you want to close on backdrop click
 * @param backContent - composable for backdrop content
 */
data class AlertConfiguration(
    val maxHeight: Float? = null,
    val maxWidth: Float? = null,
    val cornerRadius: Int = 0,
    val alpha: Float = 0.2f,
    val closeOnBackdropClick: Boolean = true
) : ModalConfiguration

object CustomModalConfiguration : ModalConfiguration

interface ModalConfiguration