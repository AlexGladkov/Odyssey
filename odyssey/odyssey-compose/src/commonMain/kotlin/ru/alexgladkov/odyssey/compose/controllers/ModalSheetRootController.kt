package ru.alexgladkov.odyssey.compose.controllers

import kotlinx.coroutines.flow.MutableStateFlow
import ru.alexgladkov.odyssey.compose.Render
import ru.alexgladkov.odyssey.compose.navigation.bottom_sheet_navigation.ModalSheetConfiguration
import ru.alexgladkov.odyssey.core.extensions.CFlow
import ru.alexgladkov.odyssey.core.extensions.wrap

/**
 * Class helper to use with compose
 * @param maxHeight - maxHeight in Float
 * @param cornerRadius - card corner radius in dp
 * @param alpha - scrimer alpha
 * @param closeOnBackdropClick - true if you want to close on backdrop click
 * @param content - composable content
 */
data class ModalSheetBundle(
    val maxHeight: Float,
    val closeOnBackdropClick: Boolean,
    val alpha: Float,
    val cornerRadius: Int,
    val content: Render
)

/**
 * Class controller for bottom modal sheet
 */
class ModalSheetController {
    private var _backStack = mutableListOf<ModalSheetBundle>()
    private val _currentStack: MutableStateFlow<List<ModalSheetBundle>> = MutableStateFlow(emptyList())
    val currentStack: CFlow<List<ModalSheetBundle>> = _currentStack.wrap()

    fun presentNew(modalSheetConfiguration: ModalSheetConfiguration,
                   content: Render) {
        _backStack.add(modalSheetConfiguration.wrap(content))
        redrawStack()
    }

    fun removeTopScreen() {
        if (_backStack.isNotEmpty()) _backStack.removeLast()
        redrawStack()
    }

    fun isEmpty() = _backStack.isEmpty()

    private fun redrawStack() {
        val newStack = ArrayList<ModalSheetBundle>().apply {
            addAll(_backStack)
        }
        _currentStack.value = newStack
    }
}

internal fun ModalSheetConfiguration.wrap(with: Render): ModalSheetBundle = ModalSheetBundle(
    maxHeight = maxHeight,
    closeOnBackdropClick = closeOnBackdropClick,
    cornerRadius = cornerRadius,
    alpha = alpha,
    content = with
)