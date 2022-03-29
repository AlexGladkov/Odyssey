package ru.alexgladkov.odyssey.compose.controllers

import kotlinx.coroutines.flow.MutableStateFlow
import ru.alexgladkov.odyssey.compose.Render
import ru.alexgladkov.odyssey.compose.navigation.bottom_sheet_navigation.AlertConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_sheet_navigation.CustomModalConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_sheet_navigation.ModalSheetConfiguration
import ru.alexgladkov.odyssey.core.extensions.CFlow
import ru.alexgladkov.odyssey.core.extensions.wrap

/**
 * Class helper to use with compose for bottom modal sheet
 * @param maxHeight - maxHeight in Float. use null for wrap by content
 * @param cornerRadius - card corner radius in dp
 * @param alpha - scrimer alpha
 * @param closeOnBackdropClick - true if you want to close on backdrop click
 * @param content - composable content
 */
data class ModalSheetBundle(
    val maxHeight: Float?,
    val closeOnBackdropClick: Boolean,
    val alpha: Float,
    val cornerRadius: Int,
    val backContent: Render? = null,
    val content: Render
) : ModalBundle

/**
 * Class helper to use with compose for alert dialog
 * @param maxHeight - maxHeight in Float. use null for wrap by content
 * @param maxWidth - maxWidth in Float, use null for wrap by content
 * @param closeOnBackdropClick - true if you want to close on backdrop click
 * @param cornerRadius - card corner radius in dp
 * @param alpha - scrimer alpha
 * @param content - composable content
 */
data class AlertBundle(
    val maxHeight: Float?,
    val maxWidth: Float?,
    val closeOnBackdropClick: Boolean,
    val alpha: Float,
    val cornerRadius: Int,
    val content: Render
) : ModalBundle

/**
 * Class helper to use with modal for custom modal
 */
data class CustomModalBundle(
    val content: Render
) : ModalBundle

interface ModalBundle

@Deprecated("see ModalSheetController", ReplaceWith("ModalSheetController"))
class ModalSheetController : ModalController()

/**
 * Class controller for bottom modal sheet
 */
open class ModalController {
    private var _backStack = mutableListOf<ModalBundle>()
    private val _currentStack: MutableStateFlow<List<ModalBundle>> = MutableStateFlow(emptyList())
    val currentStack: CFlow<List<ModalBundle>> = _currentStack.wrap()

    internal fun presentNew(
        modalSheetConfiguration: ModalSheetConfiguration,
        content: Render
    ) {
        _backStack.add(modalSheetConfiguration.wrap(content))
        redrawStack()
    }

    internal fun presentNew(
        alertConfiguration: AlertConfiguration,
        content: Render
    ) {
        _backStack.add(alertConfiguration.wrap(content))
        redrawStack()
    }

    internal fun presentNew(
        customConfiguration: CustomModalConfiguration,
        content: Render
    ) {
        _backStack.add(customConfiguration.wrap(content))
        redrawStack()
    }

    fun popBackStack() {
        if (_backStack.isNotEmpty()) _backStack.removeLast()
        redrawStack()
    }

    @Deprecated("@see popBackStack", ReplaceWith("popBackStack()"))
    fun removeTopScreen() {
        popBackStack()
    }

    fun clearBackStack() {
        _backStack.clear()
    }

    fun isEmpty() = _backStack.isEmpty()

    private fun redrawStack() {
        val newStack = ArrayList<ModalBundle>().apply {
            addAll(_backStack)
        }
        _currentStack.value = newStack
    }
}

internal fun ModalSheetConfiguration.wrap(with: Render): ModalBundle = ModalSheetBundle(
    maxHeight = maxHeight,
    closeOnBackdropClick = closeOnBackdropClick,
    cornerRadius = cornerRadius,
    alpha = alpha,
    backContent = backContent,
    content = with
)

internal fun AlertConfiguration.wrap(with: Render): ModalBundle = AlertBundle(
    maxHeight = maxHeight,
    maxWidth = maxWidth,
    closeOnBackdropClick = closeOnBackdropClick,
    cornerRadius = cornerRadius,
    alpha = alpha,
    content = with
)

@Suppress("unused")
internal fun CustomModalConfiguration.wrap(with: Render): ModalBundle = CustomModalBundle(
    content = with
)