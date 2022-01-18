package ru.alexgladkov.odyssey.compose.controllers

import kotlinx.coroutines.flow.MutableStateFlow
import ru.alexgladkov.odyssey.compose.Render
import ru.alexgladkov.odyssey.compose.RenderWithParams
import ru.alexgladkov.odyssey.core.extensions.CFlow
import ru.alexgladkov.odyssey.core.extensions.wrap

data class ModalSheetBundle(
    val peekHeight: Int,
    val closeOnBackdropClick: Boolean,
    val cornerRadius: Int,
    val content: Render
)

data class ModalSheetConfiguration(
    val peekHeight: Int,
    val cornerRadius: Int = 0,
    val closeOnBackdropClick: Boolean = true,
)

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

    private fun redrawStack() {
        val newStack = ArrayList<ModalSheetBundle>().apply {
            addAll(_backStack)
        }
        _currentStack.value = newStack
    }
}

internal fun ModalSheetConfiguration.wrap(with: Render): ModalSheetBundle = ModalSheetBundle(
    peekHeight = peekHeight,
    closeOnBackdropClick = closeOnBackdropClick,
    cornerRadius = cornerRadius,
    content = with
)