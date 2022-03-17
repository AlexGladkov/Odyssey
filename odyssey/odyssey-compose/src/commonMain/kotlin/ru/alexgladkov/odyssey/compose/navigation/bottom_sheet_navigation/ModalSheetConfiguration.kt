package ru.alexgladkov.odyssey.compose.navigation.bottom_sheet_navigation

/**
 * Class configurator for modal sheet controller
 * @param maxHeight - maxHeight in Float. use null for wrap by content
 * @param cornerRadius - card corner radius in dp
 * @param alpha - scrimer alpha
 * @param closeOnBackdropClick - true if you want to close on backdrop click
 */
data class ModalSheetConfiguration(
    val maxHeight: Float? = null,
    val cornerRadius: Int = 0,
    val alpha: Float = 0.2f,
    val closeOnBackdropClick: Boolean = true,
)